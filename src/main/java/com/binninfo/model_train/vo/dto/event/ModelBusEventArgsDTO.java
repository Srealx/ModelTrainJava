package com.binninfo.model_train.vo.dto.event;

import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型业务事件参数dto
 * @Author chengang
 * @Date 2025/7/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelBusEventArgsDTO {
    /**
     * 事件所属模型
     */
    @JsonIgnore
    private ModelEnum modelEnum;
    /**
     * 任务类型
     */
    @JsonIgnore
    private ModelTaskTypeEnum modelTaskTypeEnum;
}
