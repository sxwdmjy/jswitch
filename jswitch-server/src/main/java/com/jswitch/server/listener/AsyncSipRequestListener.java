package com.jswitch.server.listener;

import com.jswitch.server.factory.SipRequestStrategy;
import com.jswitch.server.factory.SipMessageStrategyFactory;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.msg.SipMessageListener;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
@Component
public class AsyncSipRequestListener implements SipMessageListener, InitializingBean {

    private ExecutorService executorService;

    @Autowired
    private SipMessageStrategyFactory strategyFactory;


    private SipResponse processMessage(SipMessageEvent event) {
        SipRequest message =(SipRequest) event.getMessage();
        log.info("收到SipRequest消息：\n\n" + message);
        // 获取消息的第一行
        SipRequestStrategy strategy = strategyFactory.getSipRequestStrategy(message.getMethod());
        if (strategy != null) {
            return strategy.handle(message);
        } else {
            log.info("No strategy found for message type: " + message.getMethod());
            SipRequestStrategy nostrategy = strategyFactory.getSipRequestStrategy("NOSTRATEGY");
           return nostrategy.handle(message);
        }
    }

    @Override
    public void onMessage(SipMessageEvent event) {
        CompletableFuture.supplyAsync(() -> processMessage(event), executorService)
                .thenAccept(result -> {
                    // 处理完成后的操作，例如通知其他组件
                    event.getCtx().channel().eventLoop().execute(() -> {
                        log.info("执行完成 发送数据：\n{} 结束", result);
                        if(Objects.nonNull(result)){
                            event.getCtx().writeAndFlush(result.toString());
                        }
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
                return new Thread(r, "AsyncSipRequestListener");
            }
        }, new ThreadPoolExecutor.AbortPolicy());
    }
}
