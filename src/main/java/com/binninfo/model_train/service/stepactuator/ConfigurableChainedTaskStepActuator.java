package com.binninfo.model_train.service.stepactuator;

import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;

/**
 * @Author chengang
 * @Date 2025/6/18
 */
public abstract class ConfigurableChainedTaskStepActuator<R extends TaskStepResultDTO, P extends TaskStepExecuteDTO> implements IConfigurableChainedTaskStepActuator<R,P> {

    private IConfigurableChainedTaskStepActuator<R, P> nextNode;

    @Override
    public IConfigurableChainedTaskStepActuator<R,P> getNextNode() {
        return this.nextNode;
    }

    @Override
    public void setNextNode(IConfigurableChainedTaskStepActuator<R, P> nextNode) {
        this.nextNode = nextNode;
    }

}
