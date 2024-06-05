package com.jswitch.server.listener;

import com.jswitch.server.factory.SipMessageStrategyFactory;
import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.msg.SipMessageListener;
import com.jswitch.sip.SipRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Slf4j
@Component
public class AsyncSipRequestListener implements SipMessageListener, InitializingBean {

    private ExecutorService executorService;

    private final SipMessageStrategyFactory strategyFactory;

    public AsyncSipRequestListener(SipMessageStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }


    private void processMessage(SipMessageEvent event) {
        SipRequest message = (SipRequest) event.getMessage();
        log.info("收到SipRequest消息：\n\n" + message);
        // 获取消息的第一行
        try {
            SipMessageStrategy strategy = strategyFactory.getSipRequestStrategy(message.getMethod());
            if (strategy != null) {
                strategy.handle(event);
            } else {
                log.info("No strategy found for message type: " + message.getMethod());
                SipMessageStrategy nostrategy = strategyFactory.getSipRequestStrategy("NOSTRATEGY");
                nostrategy.handle(event);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage(SipMessageEvent event) {
        executorService.execute(() -> processMessage(event));
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
                return new Thread(r, "AsyncSipRequestListener");
            }
        }, new ThreadPoolExecutor.AbortPolicy());
    }
}
