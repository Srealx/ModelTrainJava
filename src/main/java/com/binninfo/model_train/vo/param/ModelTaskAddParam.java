package com.binninfo.model_train.vo.param;

import cn.hutool.core.text.StrPool;
import com.binninfo.model_train.constant.bus.TaskTypeConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 模型训练任务新增param
 * @author chengang
 * @date 2025/5/12
 */
@Data
public class ModelTaskAddParam {
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    private String taskName;
    /**
     * 模型版本id
     */
    @NotNull(message = "模型版本id不能为空")
    private Integer modelVersionId;
    /**
     * 上传文件uuid
     */
    @NotBlank(message = "上传文件uuid不能为空")
    private String fileUuid;
    /**
     * 上传文件后缀
     */
    @NotBlank(message = "上传文件后缀格式不能为空")
    private String fileSuffix;

    /**
     * 任务类型 {@link TaskTypeConstant}
     */
    @NotNull(message = "任务类型不能为空")
    protected Integer taskType;

    public void setFileSuffix(String fileSuffix){
        if (Boolean.FALSE.equals(fileSuffix.startsWith(StrPool.DOT))){
            fileSuffix = StrPool.DOT + fileSuffix;
        }
        this.fileSuffix = fileSuffix;
    }
}
