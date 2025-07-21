package com.binninfo.model_train.service.delay;

import cn.hutool.core.text.StrPool;
import com.binninfo.model_train.config.DelayProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author chengang
 * @Date 2025/7/10
 */
@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class DelayQueueProvider {

    private final RBlockingQueue<String> destinationQueue;
    private final RDelayedQueue<String> delayedQueue;

    private final DelayProperties delayProperties;

    public void addDelayedTask(String sign, String content, long delay, TimeUnit timeUnit) {
        String task = delayProperties.getQueueName() + StrPool.COLON + sign + StrPool.COLON + content;
        delayedQueue.offer(task, delay, timeUnit);
        log.info("——————————————————>>>  延迟队列:  添加延迟任务: {}, 延迟时间: {} {}", task, delay, timeUnit);
    }

    public String poll(){
        try {
            return destinationQueue.poll(
                    delayProperties.getPollTimeoutSec(),
                    TimeUnit.SECONDS
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("——————————————————>>>   延迟队列：  消费者线程被中断");
        } catch (Exception e) {
            log.error("——————————————————>>>   延迟队列：  处理任务时发生异常", e);
        }
        return null;
    }

    public void destroy(){
        delayedQueue.destroy();
    }
}
