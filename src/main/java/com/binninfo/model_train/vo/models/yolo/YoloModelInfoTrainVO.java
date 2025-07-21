package com.binninfo.model_train.vo.models.yolo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模型训练详情数据-正式数据vo
 * @Author chengang
 * @Date 2025/5/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class YoloModelInfoTrainVO extends YoloModelTaskInfoVO {
    /**
     * 训练轮数
     */
    private Integer roundCount;
    /**
     * 训练集占比
     */
    private Float trainCollectProportion;
    /**
     * 评估集占比
     */
    private Float assessmentCollectProportion;
    /**
     * 训练集与评估集占比
     */
    private String proportion;
}
