package com.jswitch.server.transaction;

import com.jswitch.common.enums.TransactionStateEnum;
import com.jswitch.server.cache.SipDialogManageCache;
import com.jswitch.server.cache.TransactionManageCache;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.sip.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author danmo
 * @date 2024-07-02 14:26
 **/
@Data
public class ServerSipTransaction implements SipTransaction {

    /**
     * TU
     */
    private TransactionUser transactionUser;

    /**
     * 状态
     */
    private TransactionStateEnum state;

    /**
     * 最后一次请求
     */
    private Request lastRequest;

    /**
     * 最后一个临时响应
     */
    private Response lastProvisionalResponse;
    /**
     * 最后一次响应
     */
    private Response lastFinalResponse;


    private String transactionId;

    /**
     * 事件消息
     */
    private SipMessageEvent eventMsg;

    private SipDialog sipDialog;

    private final long T1 = 500;  // T1 默认值为500ms
    private final long T2 = 4000; // T2 默认值为4s
    private final long T4 = 5000; // T4 默认值为5s

    private ScheduledThreadPoolExecutor timerTemporary;
    private ScheduledThreadPoolExecutor timerG;
    private ScheduledThreadPoolExecutor timerH;
    private ScheduledThreadPoolExecutor timerI;

    public ServerSipTransaction(SipMessageEvent event, TransactionUser transactionUser) {
        this.state = TransactionStateEnum.INITIAL;
        this.eventMsg = event;
        this.transactionUser = transactionUser;
        this.transactionId = event.getMessage().getTransactionId();
    }


    @Override
    public void processRequest() {
        SipRequest sipRequest = (SipRequest) eventMsg.getMessage();
        if (Objects.equals(sipRequest.getMethod(), Request.INVITE)) {
            if (state == TransactionStateEnum.INITIAL) {
                state = TransactionStateEnum.PROCEEDING;
                lastRequest = sipRequest;
                // 模拟检查200ms内是否能生成响应
                timerTemporary = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("timerTemporary"));
                timerTemporary.schedule(() -> {
                    if (state == TransactionStateEnum.PROCEEDING) {
                        // 生成100 (Trying)响应
                        SipResponse response = sipRequest.createResponse(100);
                        sendResponse(response);
                    }
                }, 200, TimeUnit.MILLISECONDS);

                if (StringUtils.isEmpty(sipRequest.getToTag())) {
                    // 如果接收的请求头中的To头域没有tag标志，那么原来描述的 可以增加tag标记，更改成为 不应该增加tag标志
                }
                //将请求交给TU处理
                transactionUser.sendRequest(sipRequest, eventMsg.getCtx());
            } else if (state == TransactionStateEnum.PROCEEDING && isRetransmission(eventMsg)) {
                // 如果是重发请求，重新发送最后一个临时响应
                if (lastProvisionalResponse != null) {
                    System.out.println("Retransmission detected. Resending last provisional response: " + lastProvisionalResponse);
                    sendToTransportLayer(eventMsg.getCtx(), lastProvisionalResponse);
                }
            }
        } else if (Objects.equals(sipRequest.getMethod(), Request.ACK)) {
            if (state == TransactionStateEnum.COMPLETED) {
                state = TransactionStateEnum.CONFIRMED;
                timerG.shutdownNow();

                timerI = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("serverTransaction timerI"));
                long timeIDelay = T4;
                if (isReliableTransport(sipRequest)) {
                    timeIDelay = 0;
                }
                timerI.schedule(() -> {
                    state = TransactionStateEnum.TERMINATED;
                    close(sipRequest.getTransactionId());
                }, timeIDelay, TimeUnit.MILLISECONDS);
            }
        }

    }


    @Override
    public void sendResponse(Response response) {
        SipRequest sipRequest = (SipRequest) eventMsg.getMessage();
        if (response instanceof SipResponse sipResponse) {
            if (sipResponse.getStatusCode() >= 200 && sipResponse.getStatusCode() < 300) {
                state = TransactionStateEnum.TERMINATED;
                // 停止定时任务
                timerTemporary.shutdownNow();
                sendToTransportLayer(eventMsg.getCtx(), sipResponse);
                close(sipRequest.getTransactionId());

                if (Objects.isNull(sipDialog) && sipResponse.getStatusCode() == 200) {
                    if (!SipDialogManageCache.isExist(sipRequest.getDialogId(true))) {
                        SipDialog dialog = SipDialog.createDialogFromResponse((SipResponse) response, sipRequest);
                        SipDialogManageCache.saveSipDialog(dialog);
                    } else {
                        SipDialog sipDialog = SipDialogManageCache.getSipDialog(sipRequest.getDialogId(true));
                        sipDialog.setState(DialogState.COMPLETED);
                    }
                }
            } else if (sipResponse.getStatusCode() >= 300 && sipResponse.getStatusCode() < 700) {
                if (state == TransactionStateEnum.PROCEEDING) {
                    this.lastFinalResponse = sipResponse;
                    sendToTransportLayer(eventMsg.getCtx(), sipResponse);
                    state = TransactionStateEnum.COMPLETED;
                    if (!isReliableTransport(sipResponse)) {
                        timerG = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("serverTransaction timerG"));
                        timerG.schedule(new Runnable() {
                            @Override
                            public void run() {
                                sendToTransportLayer(eventMsg.getCtx(), lastFinalResponse);
                                long nextG = Math.min(2 * T1, T2);
                                timerG.schedule(this, nextG, TimeUnit.MILLISECONDS);
                            }
                        }, T1, TimeUnit.MILLISECONDS);
                    }

                    if (!SipDialogManageCache.isExist(sipRequest.getDialogId(true))) {
                        SipDialogManageCache.removeSipDialog(sipRequest.getDialogId(true));
                    }

                    timerH = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("serverTransaction TimeH"));
                    timerH.schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (state == TransactionStateEnum.COMPLETED) {
                                state = TransactionStateEnum.TERMINATED;
                                transactionUser.sendResponseError(new SipMessageEvent(sipResponse, eventMsg.getCtx()));
                                close(sipRequest.getTransactionId());
                            } else if (state == TransactionStateEnum.TERMINATED) {
                                timerH.shutdownNow();
                                close(sipRequest.getTransactionId());
                            }
                        }
                    }, 64 * T1, TimeUnit.MILLISECONDS);
                }
            } else {
                if (state == TransactionStateEnum.PROCEEDING) {
                    lastProvisionalResponse = sipResponse;
                    sendToTransportLayer(eventMsg.getCtx(), sipResponse);
                }

                if (!SipDialogManageCache.isExist(sipRequest.getDialogId(true))) {
                    SipDialog dialog = SipDialog.createDialogFromResponse(sipResponse, sipRequest);
                    SipDialogManageCache.saveSipDialog(dialog);
                }
            }
        }
    }

    private boolean isRetransmission(SipMessageEvent event) {
        // 检查请求是否与上一个请求相同（重发请求）
        return lastRequest != null && lastRequest.equals(event.getMessage());
    }

    private boolean isReliableTransport(SipMessage sipMessage) {
        // 检查是否使用可靠传输协议（例如TCP）
        String transport = sipMessage.getTopmostVia().getTransport();
        if ("tcp".equalsIgnoreCase(transport)) {
            return true;
        } else {
            return false;
        }
    }

    private void sendToTransportLayer(ChannelHandlerContext ctx, Response response) {
        ctx.writeAndFlush(response);
    }

    private void close(String transactionId) {
        if (timerTemporary != null) {
            timerTemporary.shutdown();
        }
        if (timerG != null) {
            timerG.shutdown();
        }
        if (timerH != null) {
            timerH.shutdown();
        }
        if (timerI != null) {
            timerI.shutdown();
        }
        TransactionManageCache.removeServerTransaction(transactionId);
    }
}
