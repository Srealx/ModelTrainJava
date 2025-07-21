package com.binninfo.model_train.vo.dto.event;

import com.binninfo.model_train.service.stepactuator.ModelTaskChainedTaskStepActuatorStep;
import com.binninfo.model_train.vo.param.ModelTaskCallBackParam;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author chengang
 * @Date 2025/7/4
 */
@Data
@AllArgsConstructor
public class ModelTaskStepEventArgsDTO {
    /**
     * 事件处理者
     */
    ModelTaskChainedTaskStepActuatorStep modelTaskChainedTaskStepActuator;
    /**
     * 回调参数
     */
    ModelTaskCallBackParam modelTaskCallBackParam;
}
