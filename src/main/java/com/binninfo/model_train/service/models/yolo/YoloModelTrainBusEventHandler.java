package com.binninfo.model_train.service.models.yolo;

import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.event.bus.ModelTaskAddEvent;
import com.binninfo.model_train.service.event.bus.ModelTaskResultDownloadUrlGetEvent;
import com.binninfo.model_train.service.event.bus.YoloModelTaskInfoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author chengang
 * @Date 2025/7/14
 */
@Component
@Slf4j
public class YoloModelTrainBusEventHandler extends YoloModelBusEventHandler {

    @Override
    public ModelTaskTypeEnum getModelTaskType() {
        return ModelTaskTypeEnum.TRAIN;
    }

    @Override
    public void handlerModelTaskAdd(ModelTaskAddEvent modelTaskAddEvent) {

    }

    @Override
    public void handlerQueryModelTaskInfo(YoloModelTaskInfoEvent yoloModelTaskInfoEvent) {
        super.handlerQueryModelTaskInfo(yoloModelTaskInfoEvent);
    }

    @Override
    public void handlerModelTaskResultDownloadUrlGet(ModelTaskResultDownloadUrlGetEvent modelTaskResultDownloadUrlGetEvent) {

    }
}
