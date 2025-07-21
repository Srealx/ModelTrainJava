package com.binninfo.model_train.vo.models.yolo;

import lombok.Data;

/**
 * 模型训练详情数据-标签数据vo
 * @Author chengang
 * @Date 2025/5/29
 */
@Data
public class YoloModelTaskInfoLabelVO {
    /**
     * 标签名
     */
    private String label;
    /**
     * 标签号
     */
    private String labelNumber;
    /**
     * 标注数
     */
    private String markCount;
    /**
     * 占比
     */
    private String proportionString;
    private Double proportion;

}
