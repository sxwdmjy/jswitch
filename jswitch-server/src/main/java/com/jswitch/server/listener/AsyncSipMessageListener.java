package com.jswitch.server.listener;

import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.factory.SipMessageStrategyFactory;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.msg.SipMessageListener;
import com.jswitch.server.msg.SipMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Slf4j
@Component
public class AsyncSipMessageListener implements SipMessageListener, InitializingBean {

    private ExecutorService executorService;

    @Autowired
    private SipMessageStrategyFactory strategyFactory;


    private String processMessage(SipMessageEvent event) {
        String message = event.getMessage();
        log.info("收到消息：\n" + message);
        if (message.split("\r\n").length == 0) {
            return "Null Message";
        }
        SipMessageRequest sipRequest = new SipMessageRequest(message);
        String channelId = event.getCtx().channel().id().asLongText();
        String remoteAddress = event.getCtx().channel().remoteAddress().toString();
        sipRequest.setChannelId(channelId);
        sipRequest.setRemoteIp(remoteAddress.substring(1, remoteAddress.lastIndexOf(':')));
        sipRequest.setRemotePort(remoteAddress.substring(remoteAddress.lastIndexOf(':') + 1));
        // 获取消息的第一行
        SipMessageStrategy strategy = strategyFactory.getStrategy(sipRequest.getMethod());
        if (strategy != null) {
            return strategy.handle(sipRequest).toString();
        } else {
            log.info("No strategy found for message type: " + sipRequest.getMethod());
        }
        return "No strategy found for message type: " + sipRequest.getMethod();
    }

    @Override
    public void onMessage(SipMessageEvent event) {
        CompletableFuture.supplyAsync(() -> processMessage(event), executorService)
                .thenAccept(result -> {
                    // 处理完成后的操作，例如通知其他组件
                    event.getCtx().channel().eventLoop().execute(() -> {
                        log.info("执行完成 发送数据：\n{} 结束", result);
                        event.getCtx().writeAndFlush(result);
                    });
                })
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    event.getCtx().close();
                    return null;
                });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取CPU核心数
        int cpuCores = Runtime.getRuntime().availableProcessors();
        // 假设I/O等待时间是CPU时间的2倍
        int ioWaitFactor = 2;
        // 计算核心线程数和最大线程数
        int corePoolSize = cpuCores * (1 + ioWaitFactor);
        int maxPoolSize = corePoolSize * 2;
        this.executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "AsyncSipMessageListener");
            }
        }, new ThreadPoolExecutor.AbortPolicy());
    }
}