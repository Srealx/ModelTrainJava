package com.binninfo.model_train.task;

import com.binninfo.model_train.service.impl.ModelTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 模型训练任务-定时器
 * @Author chengang
 * @Date 2025/6/16
 */
@Component
@Slf4j
public class SysTask {
    @Resource
    private ModelTaskServiceImpl modelTrainTaskService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void modelTrain(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=====   模型训练-定时器开始执行");
        modelTrainTaskService.task();
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=====   模型训练-定时器结束");
    }
}
