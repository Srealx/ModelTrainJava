package com.binninfo.model_train.vo.models.yolo;

import com.binninfo.model_train.constant.bus.TaskTypeConstant;
import com.binninfo.model_train.vo.param.ModelTaskAddParam;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模型训练任务新增param
 * @author chengang
 * @date 2025/5/12
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "taskType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = YoloModelTaskFormalAddParam.class, name = TaskTypeConstant.TRAIN_STRING),
        @JsonSubTypes.Type(value = YoloModelTaskValAddParam.class, name = TaskTypeConstant.VAL_STRING)
})
@Data
@EqualsAndHashCode(callSuper = true)
public class YoloModelTaskAddParam extends ModelTaskAddParam {

}
