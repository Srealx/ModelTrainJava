package com.binninfo.model_train.vo.models.yolo;

import com.binninfo.model_train.vo.models.yolo.YoloModelTaskInfoLabelVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模型训练详情数据-标签数据vo
 * @Author chengang
 * @Date 2025/5/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class YoloModelTaskInfoLabelFormalVO extends YoloModelTaskInfoLabelVO {
    /**
     * 精确率
     */
    private Double accuracy;
    private String accuracyString;

    /**
     * 召回率
     */
    private Double recall;
    private String recallString;

    /**
     * 平均精度
     */
    private Double averageAccuracy;
    private String averageAccuracyString;



    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
        if (accuracy != null){
            this.accuracyString = String.format("%.2f",accuracy*100) + "%";
        }
    }

    public void setRecall(Double recall) {
        this.recall = recall;
        if (recall != null){
            this.recallString = String.format("%.2f",recall*100) + "%";
        }
    }

    public void setAverageAccuracy(Double averageAccuracy) {
        this.averageAccuracy = averageAccuracy;
        if (averageAccuracy != null){
            this.averageAccuracyString = String.format("%.2f",averageAccuracy*100) + "%";
        }
    }
}
