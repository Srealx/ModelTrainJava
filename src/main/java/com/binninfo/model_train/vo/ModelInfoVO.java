package com.binninfo.model_train.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 模型列表详情vo
 * @Author chengang
 * @Date 2025/6/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelInfoVO extends ModelListVO {
    /**
     * 版本列表
     */
    private List<ModelVersionVO> versionList;
}
