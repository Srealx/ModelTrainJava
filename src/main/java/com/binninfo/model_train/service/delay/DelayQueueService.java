package com.binninfo.model_train.service.delay;

import cn.hutool.core.text.StrPool;
import com.binninfo.model_train.config.DelayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 延时队列服务
 * @Author chengang
 * @Date 2025/7/9
 */
@Slf4j
@Service
public class DelayQueueService implements CommandLineRunner  {

    @Resource
    DelayQueueProvider delayQueueProvider;

    private final DelayProperties delayProperties;

    private ExecutorService executorService;
    private final AtomicBoolean running = new AtomicBoolean(true);

    List<IDelayHandler> delayHandlerList;
    Map<String,IDelayHandler> delayHandlerMap;

    public DelayQueueService(DelayProperties delayProperties, List<IDelayHandler> delayHandlerList) {
        this.delayProperties = delayProperties;
        this.delayHandlerList = delayHandlerList;
    }

    @Override
    public void run(String... args) {
        executorService = Executors.newFixedThreadPool(delayProperties.getConsumerThreads());
        for (int i = 0; i < delayProperties.getConsumerThreads(); i++) {
            executorService.submit(this::consume);
        }
        log.info("——————————————————>>>   启动 {} 个延迟队列监听线程", delayProperties.getConsumerThreads());
        delayHandlerMap = delayHandlerList.stream().collect(Collectors.toMap(IDelayHandler::getSign, Function.identity()));
    }

    public void consume() {
        while (running.get()) {
            String task = delayQueueProvider.poll();
            if (task != null) {
                processTask(task);
            }
        }
        log.info("消费者线程退出");
    }


    private void processTask(String task) {
        log.info("——————————————————>>>   延迟队列: 开始处理任务: {}", task);
        String[] split = task.split(StrPool.COLON);
        log.info("——————————————————>>>  延迟队列： 签名: {} 的 key {} 到期, 开始处理",split[0],split[1]);
        delayHandlerMap.get(split[1]).handlerDelay(split.length > 2 ? split[2] : "");
        log.info("——————————————————>>>   延迟队列: 任务处理完成: {}", task);
    }

    @PreDestroy
    public void destroy() {
        running.set(false);
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        delayQueueProvider.destroy();
        log.info("——————————————————>>>   延迟队列服务已关闭");
    }


}
