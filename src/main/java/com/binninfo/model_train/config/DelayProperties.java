package com.binninfo.model_train.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "delay-queue")
public class DelayProperties {
    private String queueName = "delayed_tasks";
    private int consumerThreads;
    private int pollTimeoutSec;
    private int modelTaskSec;
}