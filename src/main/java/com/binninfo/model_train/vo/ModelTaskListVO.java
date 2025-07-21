package com.binninfo.model_train.vo;

import com.binninfo.excelcommon.enumtranslate.annotation.EnumTranslate;
import com.binninfo.model_train.constant.enums.ModelTaskStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 模型任务列表vo
 * @Author chengang
 * @Date 2025/5/28
 */
@Data
public class ModelTaskListVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 模型名称
     */
    @JsonIgnore
    private String model;
    @JsonIgnore
    private Integer modelVersionId;
    private String modelName;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    @EnumTranslate(codeField = "taskStatus",enumClass = ModelTaskStatusEnum.class)
    private String taskStatusString;

    /**
     * 步骤数据
     */
    private List<ModelTaskStepDataVO> modelTaskStepDataList;

    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimeString;
}
