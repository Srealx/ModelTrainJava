package com.binninfo.model_train.vo.models.yolo;

import com.binninfo.model_train.vo.ModelTaskListVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * yolo-模型任务-评估任务下挂数据
 * @Author chengang
 * @Date 2025/5/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class YoloModelTaskValListVO extends ModelTaskListVO {
    /**
     * 准确率
     */
    private Double accuracy;
    private String accuracyString;
}
