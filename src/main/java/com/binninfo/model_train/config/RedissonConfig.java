package com.binninfo.model_train.config;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class RedissonConfig {

    @Bean
    public RBlockingQueue<String> blockingQueue(RedissonClient redissonClient,
                                                DelayProperties delayProperties) {
        return redissonClient.getBlockingQueue(delayProperties.getQueueName());
    }

    @Bean
    public RDelayedQueue<String> delayedQueue(RedissonClient redissonClient,
                                              RBlockingQueue<String> blockingQueue) {
        return redissonClient.getDelayedQueue(blockingQueue);
    }
}