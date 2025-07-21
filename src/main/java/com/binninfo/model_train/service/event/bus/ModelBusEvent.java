package com.binninfo.model_train.service.event.bus;

import com.binninfo.model_train.vo.dto.event.ModelBusEventArgsDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 模型业务事件
 * @Author chengang
 * @Date 2025/7/7
 */
@Getter
public class ModelBusEvent<T extends ModelBusEventArgsDTO> extends ApplicationEvent {

    T modelBusEventArgsDTO;

    public ModelBusEvent(Object source, T modelBusEventArgsDTO) {
        super(source);
        this.modelBusEventArgsDTO = modelBusEventArgsDTO;
    }

    protected void setModelBusEventArgsDTO(T modelBusEventArgsDTO){
        this.modelBusEventArgsDTO = modelBusEventArgsDTO;
    }
}
