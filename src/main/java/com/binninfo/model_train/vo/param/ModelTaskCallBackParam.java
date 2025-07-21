package com.binninfo.model_train.vo.param;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 模型任务回调param
 * @Author chengang
 * @Date 2025/6/27
 */
@Data
@ToString
public class ModelTaskCallBackParam {
    /**
     * 任务uuid
     */
    @NotBlank(message = "uuid必传")
    private String uuid;
    /**
     * 步骤编码
     */
    @NotNull(message = "progressCode必传")
    private Integer progressCode;
    /**
     * 是否成功
     */
    @NotNull(message = "是否成功必传")
    private Boolean success;
    /**
     * 相关信息
     */
    private String msg;
    /**
     * 执行日志
     */
    private String content;
    /**
     * 执行结果
     */
    private Object result;
}
