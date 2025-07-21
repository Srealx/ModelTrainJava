package com.binninfo.model_train.service.event.task;

import com.binninfo.model_train.vo.dto.event.ModelTaskStepEventArgsDTO;
import lombok.Getter;

/**
 * 事件-处理回调之前处理
 * @Author chengang
 * @Date 2025/7/4
 */
@Getter
public class BeforeHandlerCallbackStartStepEvent extends ModelTaskStepEvent {
    public BeforeHandlerCallbackStartStepEvent(Object source, ModelTaskStepEventArgsDTO modelTaskStepEventArgsDTO) {
        super(source, modelTaskStepEventArgsDTO);
    }
}
