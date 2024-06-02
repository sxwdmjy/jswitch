package com.jswitch.server.listener;

import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.factory.SipMessageStrategyFactory;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.msg.SipMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.*;

@Slf4j
@Component
public class AsyncSipMessageListener implements SipMessageListener, InitializingBean {

    // 获取CPU核心数
    private final int cpuCores = Runtime.getRuntime().availableProcessors();
    // 假设I/O等待时间是CPU时间的2倍
    private final int ioWaitFactor = 2;
    // 计算核心线程数和最大线程数
    private final int corePoolSize = cpuCores * (1 + ioWaitFactor);
    private final int maxPoolSize = corePoolSize * 2;

    private ExecutorService executorService;

    @Autowired
    private SipMessageStrategyFactory strategyFactory;


    private String processMessage(SipMessageEvent event) {
        String message = event.getMessage();
        log.info("收到消息：" + message);
        if(message.split("\r\n").length == 0){
            return "Null Message";
        }
        // 获取消息的第一行
        String firstLine = message.split("\r\n")[0];
        String messageType = firstLine.split(" ")[0];
        SipMessageStrategy strategy = strategyFactory.getStrategy(messageType);
        if (strategy != null) {
            return strategy.handle(message);
        } else {
            log.info("No strategy found for message type: " + messageType);
        }
        return "No strategy found for message type: " + messageType;
    }

    @Override
    public void onMessage(SipMessageEvent event) {
        CompletableFuture.supplyAsync(() -> processMessage(event), executorService)
                .thenAccept(result -> {
                    // 处理完成后的操作，例如通知其他组件
                    event.getCtx().channel().eventLoop().execute(() -> {
                        log.info("执行完成 发送数据：{} 结束", result);
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
        this.executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "AsyncSipMessageListener");
            }
        }, new ThreadPoolExecutor.AbortPolicy());
    }
}
