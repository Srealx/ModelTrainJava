package com.binninfo.model_train.service.models;

import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;

/**
 * @Author chengang
 * @Date 2025/7/14
 */
public interface IModelTaskTypeBusEventHandler extends IModelBusEventHandler{
    /**
     * 获取任务类型
     * @return {@link ModelTaskTypeEnum}
     */
    ModelTaskTypeEnum getModelTaskType();
}
