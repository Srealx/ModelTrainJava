package com.binninfo.model_train.service.event.task;

import com.binninfo.model_train.vo.dto.event.ModelTaskStepEventArgsDTO;

/**
 * 事件-回调方法返回之前
 * @Author chengang
 * @Date 2025/7/4
 */
public class BeforeCallbackReturnStepEvent extends ModelTaskStepEvent {

    public BeforeCallbackReturnStepEvent(Object source, ModelTaskStepEventArgsDTO modelTaskStepEventArgsDTO) {
        super(source, modelTaskStepEventArgsDTO);
    }

}
