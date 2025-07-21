package com.binninfo.model_train.service.event.bus;

import com.binninfo.model_train.vo.ModelTaskInfoVO;
import lombok.Getter;

/**
 * @Author chengang
 * @Date 2025/7/14
 */
@Getter
public class YoloModelTaskInfoEvent extends ModelBusEvent<ModelTaskInfoVO>{

    public YoloModelTaskInfoEvent(Object source, ModelTaskInfoVO modelTaskInfoVO) {
        super(source,modelTaskInfoVO);
    }

    public void setModelTaskInfoVO(ModelTaskInfoVO modelTaskInfoVO){
        super.setModelBusEventArgsDTO(modelTaskInfoVO);
    }
}
