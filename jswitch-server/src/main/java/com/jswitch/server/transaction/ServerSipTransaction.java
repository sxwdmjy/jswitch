package com.jswitch.server.transaction;

import com.jswitch.common.enums.TransactionStateEnum;
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

    private Response lastFinalResponse;


    private final long T1 = 500;  // T1 默认值为500ms
    private final long T2 = 4000; // T2 默认值为4s
    private final long T4 = 5000; // T4 默认值为5s

    private ScheduledThreadPoolExecutor timerTemporary;
    private ScheduledThreadPoolExecutor timerG;
    private ScheduledThreadPoolExecutor timerH;
    private ScheduledThreadPoolExecutor timerI;

    public ServerSipTransaction() {
        this.state = TransactionStateEnum.INITIAL;
    }


    @Override
    public void processRequest(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest) event.getMessage();
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
                        sendResponse(new SipMessageEvent(response, event.getCtx()));
                    }
                }, 200, TimeUnit.MILLISECONDS);

                if (StringUtils.isEmpty(sipRequest.getToTag())) {
                    // 如果接收的请求头中的To头域没有tag标志，那么原来描述的 可以增加tag标记，更改成为 不应该增加tag标志
                }
                //将请求交给TU处理
                transactionUser.handleRequest(event);
            } else if (state == TransactionStateEnum.PROCEEDING && isRetransmission(event)) {
                // 如果是重发请求，重新发送最后一个临时响应
                if (lastProvisionalResponse != null) {
                    System.out.println("Retransmission detected. Resending last provisional response: " + lastProvisionalResponse);
                    sendToTransportLayer(event.getCtx(), lastProvisionalResponse);
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
                    close(event);
                }, timeIDelay, TimeUnit.MILLISECONDS);
            }
        }

    }


    @Override
    public void sendResponse(SipMessageEvent event) {
        if (event.getMessage() instanceof SipResponse sipResponse) {
            if (sipResponse.getStatusCode() >= 200 && sipResponse.getStatusCode() < 300) {
                state = TransactionStateEnum.TERMINATED;
                // 停止定时任务
                timerTemporary.shutdownNow();
                sendToTransportLayer(event.getCtx(), sipResponse);
                close(event);
            } else if (sipResponse.getStatusCode() >= 300 && sipResponse.getStatusCode() < 700) {
                if (state == TransactionStateEnum.PROCEEDING) {
                    this.lastFinalResponse = sipResponse;
                    sendToTransportLayer(event.getCtx(), sipResponse);
                    state = TransactionStateEnum.COMPLETED;
                    if (!isReliableTransport(sipResponse)) {
                        timerG = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("serverTransaction timerG"));
                        timerG.schedule(new Runnable() {
                            @Override
                            public void run() {
                                sendToTransportLayer(event.getCtx(), lastFinalResponse);
                                long nextG = Math.min(2 * T1, T2);
                                timerG.schedule(this, nextG, TimeUnit.MILLISECONDS);
                            }
                        }, T1, TimeUnit.MILLISECONDS);
                    }


                    timerH = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("serverTransaction TimeH"));
                    timerH.schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (state == TransactionStateEnum.COMPLETED) {
                                state = TransactionStateEnum.TERMINATED;
                                transactionUser.sendResponseError(event);
                                close(event);
                            } else if (state == TransactionStateEnum.TERMINATED) {
                                timerH.shutdownNow();
                                close(event);
                            }
                        }
                    }, 64 * T1, TimeUnit.MILLISECONDS);
                }
            } else {
                if (state == TransactionStateEnum.PROCEEDING) {
                    lastProvisionalResponse = sipResponse;
                    sendToTransportLayer(event.getCtx(), sipResponse);
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

    private void close(SipMessageEvent event) {
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
        TransactionManageCache.removeServerTransaction(event.getMessage().getTransactionId());
    }
}
