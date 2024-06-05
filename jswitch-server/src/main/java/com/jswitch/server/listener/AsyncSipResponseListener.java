package com.jswitch.server.listener;

import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.factory.SipMessageStrategyFactory;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.msg.SipMessageListener;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Slf4j
@Component
public class AsyncSipResponseListener implements SipMessageListener, InitializingBean {

    private ExecutorService executorService;

    @Autowired
    private SipMessageStrategyFactory strategyFactory;


    private void processMessage(SipMessageEvent event) {
        SipResponse response = (SipResponse) event.getMessage();
        log.info("收到SipResponse消息：\n\n" + response);

        try {
            SipMessageStrategy strategy = strategyFactory.getSipResponseStrategy(response.getStatusCode());
            if (strategy != null) {
                strategy.handle(event);
            } else {
                log.info("No strategy found for statusCode type: " + response.getStatusCode());
                SipMessageStrategy nostrategy = strategyFactory.getSipResponseStrategy(500);
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
                return new Thread(r, "AsyncSipResonseListener");
            }
        }, new ThreadPoolExecutor.AbortPolicy());
    }
}
