package com.binninfo.model_train.service.event.bus;

import com.binninfo.model_train.vo.ModelTaskListVO;
import com.binninfo.model_train.vo.dto.event.ModelTaskListEventDTO;

import java.util.List;

/**
 * @Author chengang
 * @Date 2025/7/11
 */
public class ModelTaskListQueryEvent extends ModelBusEvent<ModelTaskListEventDTO>{

    public ModelTaskListQueryEvent(Object source, ModelTaskListEventDTO modelBusEventArgsDTO) {
        super(source, modelBusEventArgsDTO);
    }

    public void setList(List<ModelTaskListVO> list){
        ModelTaskListEventDTO dto = super.getModelBusEventArgsDTO();
        dto.setList(list);
        super.setModelBusEventArgsDTO(dto);
    }

}
