package com.binninfo.model_train.vo.models.yolo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 模型训练任务正式任务新增-param
 * @author chengang
 * @date 2025/5/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class YoloModelTaskFormalAddParam extends YoloModelTaskAddParam {
    /**
     * 训练轮数
     */
    @NotNull(message = "训练轮数不能为空")
    private Integer roundCount;
    /**
     * 训练集与评估集比例
     */
    @NotBlank(message = "训练集与评估集比例不能为空")
    private String trainValProportion;
}
