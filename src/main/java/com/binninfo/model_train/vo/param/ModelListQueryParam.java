package com.binninfo.model_train.vo.param;

import com.binninfo.excelcommon.beans.page.PageableDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author chengang
 * @Date 2025/6/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelListQueryParam extends PageableDomain<ModelListQueryParam> {
    /**
     * 模型名称
     */
    private String modelName;
}
