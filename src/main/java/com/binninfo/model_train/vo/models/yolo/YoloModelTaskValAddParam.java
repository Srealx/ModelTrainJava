package com.binninfo.model_train.vo.models.yolo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * yolo 模型训练任务评估任务新增-param
 * @author chengang
 * @date 2025/5/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class YoloModelTaskValAddParam extends YoloModelTaskAddParam {
    /**
     * 模型任务数据json
     */
    String modelExtraDataJson;
}
