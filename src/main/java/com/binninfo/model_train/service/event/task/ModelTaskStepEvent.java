package com.binninfo.model_train.service.event.task;

import com.binninfo.model_train.vo.dto.event.ModelTaskStepEventArgsDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author chengang
 * @Date 2025/7/4
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class ModelTaskStepEvent extends ApplicationEvent {
    ModelTaskStepEventArgsDTO modelTaskStepEventArgsDTO;
    public ModelTaskStepEvent(Object source, ModelTaskStepEventArgsDTO modelTaskStepEventArgsDTO) {
        super(source);
        this.modelTaskStepEventArgsDTO = modelTaskStepEventArgsDTO;
    }
}
