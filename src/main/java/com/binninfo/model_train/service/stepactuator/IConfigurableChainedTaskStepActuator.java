package com.binninfo.model_train.service.stepactuator;

import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;

/**
 * @Author chengang
 * @Date 2025/6/18
 */
public interface IConfigurableChainedTaskStepActuator<R extends TaskStepResultDTO, P extends TaskStepExecuteDTO> extends IChainedTaskStepActuator<R,P>{
    /**
     * 设置下一个节点
     * @param nextNode {@link IConfigurableChainedTaskStepActuator}
     */
    void setNextNode(IConfigurableChainedTaskStepActuator<R,P> nextNode);
}
