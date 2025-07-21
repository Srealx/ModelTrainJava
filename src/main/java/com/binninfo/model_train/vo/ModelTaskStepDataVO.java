package com.binninfo.model_train.vo;

import lombok.Data;

/**
 * 模型任务-步骤数据vo
 * @Author chengang
 * @Date 2025/7/16
 */
@Data
public class ModelTaskStepDataVO {
    /**
     * 步骤名称
     */
    private String stepName;
    /**
     * 步骤code
     */
    private Integer stepCode;
    /**
     * 状态 {@link com.binninfo.model_train.constant.enums.ModelTaskStatusEnum}
     */
    private Integer status;
}
