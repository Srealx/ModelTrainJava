package com.binninfo.model_train.vo.dto.yolo;

import lombok.Data;

/**
 * 模型训练压缩包信息dto
 * @Author chengang
 * @Date 2025/5/27
 */
@Data
public class YoloPackSendResultDTO {
    /**
     * 压缩包大小
     */
    private Long dataSize;
    /**
     * 数据量
     */
    private Integer dataCount;
}
