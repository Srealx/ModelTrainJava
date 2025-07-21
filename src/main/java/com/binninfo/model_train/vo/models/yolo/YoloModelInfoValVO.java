package com.binninfo.model_train.vo.models.yolo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模型训练详情数据-测试数据vo
 * @Author chengang
 * @Date 2025/5/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class YoloModelInfoValVO extends YoloModelTaskInfoVO {
    /**
     * 准确率
     */
    private Double accuracy;
    private String accuracyString;

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
        if (accuracy != null){
            this.accuracyString = String.format("%.2f",accuracy*100) + "%";
        }
    }
}
