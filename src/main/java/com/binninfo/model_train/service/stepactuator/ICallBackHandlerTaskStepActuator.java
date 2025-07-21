package com.binninfo.model_train.service.stepactuator;

import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;

/**
 * 有回调处理的任务步骤执行器
 * @Author chengang
 * @Date 2025/6/30
 */
public interface ICallBackHandlerTaskStepActuator<C ,R extends TaskStepResultDTO, P extends TaskStepExecuteDTO> extends ITaskStepActuator<R,P> {

    /**
     * 处理回调
     * @param c C
     */
    void handlerCallBack(C c);
}
