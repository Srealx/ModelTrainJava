package com.binninfo.model_train.service.stepactuator;

import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;

/**
 * 任务步骤执行器
 * @Author chengang
 * @Date 2025/6/18
 */
public interface ITaskStepActuator<R extends TaskStepResultDTO, P extends TaskStepExecuteDTO> {

    /**
     * 构建任务参数
     * @param taskStepExecuteDTO {@link TaskStepExecuteDTO}
     * @return
     */
    P buildParam(TaskStepExecuteDTO taskStepExecuteDTO);

    /**
     * 执行
     * @param p {@link P}
     * @return {@link R}
     */
    R execute(P p);

    /**
     * 获取步骤类型
     * @return S
     */
    <S extends IProgressType> S getStep();
}
