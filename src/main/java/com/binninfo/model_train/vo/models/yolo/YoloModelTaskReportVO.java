package com.binninfo.model_train.vo.models.yolo;

import com.binninfo.model_train.vo.ModelTaskReportVO;
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
public class YoloModelTaskReportVO extends ModelTaskReportVO {
    /**
     * 训练数据图片列表
     */
    private List<String> resultReportPicList;
    /**
     * 评估集/训练集图片列表
     */
    private List<String> calculatePicList;
}
