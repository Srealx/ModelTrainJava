package com.binninfo.model_train.vo;

import lombok.Data;

/**
 * @Author chengang
 * @Date 2025/7/16
 */
@Data
public class ModelVersionListVO {
    /**
     * 模型版本id
     */
    private Integer modelVersionId;

    /**
     * 模型名称
     */
    private String modelName;
}
