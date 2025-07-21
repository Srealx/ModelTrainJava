package com.binninfo.model_train.vo.param;

import com.binninfo.excelcommon.beans.page.PageableDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 模型训练列表查询param
 * @Author chengang
 * @Date 2025/5/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelTrainListQueryParam extends PageableDomain<ModelTrainListQueryParam> {
    /**
     * 模型类型
     */
    // TODO
    private String model = "yolo";
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 模型名称
     */
    private String modelName;
    @JsonIgnore
    private List<Integer> modelVersionIdList;

    /**
     * 任务类型(0:非测试任务;  1:测试任务)
     */
    @NotNull(message = "任务类型不能为空")
    private Integer taskType;
}
