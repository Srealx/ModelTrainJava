package com.binninfo.model_train.service.event.bus;

import com.binninfo.model_train.vo.dto.event.ModelTaskAddEventDTO;

/**
 * @Author chengang
 * @Date 2025/7/14
 */
public class ModelTaskAddEvent extends ModelBusEvent<ModelTaskAddEventDTO>{

    public ModelTaskAddEvent(Object source, ModelTaskAddEventDTO modelBusEventArgsDTO) {
        super(source, modelBusEventArgsDTO);
    }
}
