package com.binninfo.model_train.vo;

import com.binninfo.model_train.vo.dto.event.ModelBusEventArgsDTO;
import lombok.Data;

/**
 * 模型列表vo
 * @Author chengang
 * @Date 2025/6/5
 */
@Data
public class ModelListVO extends ModelBusEventArgsDTO {
    private Integer id;
    /**
     * 模型名称
     */
    private String modelName;
    /**
     * 层数
     */
    private Integer numberLayers;
    /**
     * 参考量
     */
    private Double referenceQuantity;
    /**
     * 梯度
     */
    private Double gradient;
    /**
     * 计算量
     */
    private Double highComputational;
    /**
     * 训练次数
     */
    private Integer trainCount;
}
