package com.binninfo.model_train.vo.dto;

import lombok.Data;

/**
 * 模型训练任务-模型信息dto
 * @Author chengang
 * @Date 2025/6/19
 */
@Data
public class TaskModelDtaDTO {
    /**
     * 当前使用的模型名称
     */
    String modelName;

    /**
     * 当前使用模型的版本号
     */
    Integer version;

    /**
     * 该模型的最新版本号
     */
    Integer lastVersion;
}
