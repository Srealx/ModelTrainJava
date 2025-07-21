package com.binninfo.model_train.service.event.bus;

import com.binninfo.model_train.vo.ModelInfoVO;

/**
 * @Author chengang
 * @Date 2025/7/11
 */
public class ModelInfoQueryEvent extends ModelBusEvent<ModelInfoVO>{

    public ModelInfoQueryEvent(Object source, ModelInfoVO modelBusEventArgsDTO) {
        super(source, modelBusEventArgsDTO);
    }

}
