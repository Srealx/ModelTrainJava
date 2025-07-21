package com.binninfo.model_train.vo;

import com.binninfo.model_train.vo.dto.event.ModelBusEventArgsDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 模型训练任务-报表vo
 * @Author chengang
 * @Date 2025/5/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelTaskReportVO extends ModelBusEventArgsDTO {
    private Integer taskId;
    /**
     * 原数据图片列表
     */
    private List<String> originalPicList;
}
