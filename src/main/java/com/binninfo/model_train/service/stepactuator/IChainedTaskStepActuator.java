package com.binninfo.model_train.service.stepactuator;

import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;

/**
 * @Author chengang
 * @Date 2025/6/18
 */
public interface IChainedTaskStepActuator<R extends TaskStepResultDTO, P extends TaskStepExecuteDTO> extends ITaskStepActuator<R,P>, IChain<IChainedTaskStepActuator<R,P>>{

}
