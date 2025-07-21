package com.binninfo.model_train.vo.dto.yolo;

import lombok.Data;

/**
 * yolo模型训练结果数据dto
 * @Author chengang
 * @Date 2025/7/3
 */
@Data
public class YoloModelTrainResultDataDTO {
    /**
     * 标签名称
     */
    private String className;
    /**
     * 图片数
     */
    private Integer images;
    /**
     * 实例数
     */
    private Integer instances;
    /**
     * 边界预测精准度
     */
    private Float box_precision;
    /**
     * 召回率
     */
    private Float recall;
    /**
     * 准确率mAP50
     */
    private Float mAP50;
    /**
     * 准确率 mAP50_95
     */
    private Float mAP50_95;
}
