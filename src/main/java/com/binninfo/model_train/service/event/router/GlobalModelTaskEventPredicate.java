package com.binninfo.model_train.service.event.router;

import com.binninfo.model_train.service.event.task.IModelTaskStepEventHandler;
import com.binninfo.model_train.service.event.task.ModelTaskStepEvent;
import com.binninfo.model_train.service.stepactuator.ModelTaskChainedTaskStepActuatorStep;
import org.springframework.stereotype.Component;

/**
 * 全局模型任务事件断言处理器
 */
@Component
public class GlobalModelTaskEventPredicate implements ModelTaskEventPredicate {
    @Override
    public boolean evaluate(ModelTaskStepEvent event, IModelTaskStepEventHandler handler) {
        // 统一的断言逻辑, 判断模型任务处理节点是否是事件指定的节点
        if (event.getModelTaskStepEventArgsDTO().getModelTaskChainedTaskStepActuator() == null
        || handler == null){
            return Boolean.FALSE;
        }
        if (handler instanceof ModelTaskChainedTaskStepActuatorStep && event.getModelTaskStepEventArgsDTO().getModelTaskChainedTaskStepActuator() == handler){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}