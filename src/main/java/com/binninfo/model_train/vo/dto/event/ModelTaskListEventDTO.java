package com.binninfo.model_train.vo.dto.event;

import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.vo.ModelTaskListVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author chengang
 * @Date 2025/7/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelTaskListEventDTO extends ModelBusEventArgsDTO {

    List<ModelTaskListVO> list;

    public ModelTaskListEventDTO(ModelEnum modelEnum, ModelTaskTypeEnum modelTaskTypeEnum,List<ModelTaskListVO> list) {
        super(modelEnum,modelTaskTypeEnum);
        this.list = list;
    }
}
