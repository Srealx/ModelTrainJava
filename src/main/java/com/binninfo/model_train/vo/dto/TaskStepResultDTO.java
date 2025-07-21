package com.binninfo.model_train.vo.dto;

import com.binninfo.model_train.service.stepactuator.IProgressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务结果dto
 * @Author chengang
 * @Date 2025/6/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStepResultDTO {
    private Boolean success;

    private String message;

    private IProgressType stepCode;

    public static TaskStepResultDTO success(IProgressType stepCode){
        TaskStepResultDTO dto = new TaskStepResultDTO();
        dto.setSuccess(Boolean.TRUE);
        dto.setStepCode(stepCode);
        return dto;
    }

    public static TaskStepResultDTO fail(IProgressType stepCode, String message){
        TaskStepResultDTO dto = new TaskStepResultDTO();
        dto.setSuccess(Boolean.FALSE);
        dto.setStepCode(stepCode);
        dto.setMessage(message);
        return dto;
    }
}
