package com.binninfo.model_train.vo.dto.yolo;

import lombok.Data;

import java.util.List;

/**
 * yolo模型训练结果dto
 * @Author chengang
 * @Date 2025/7/3
 */
@Data
public class YoloModelTrainResultDTO {
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 数据
     */
    private List<YoloModelTrainResultDataDTO> data;
}
