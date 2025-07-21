package com.binninfo.model_train.service.event.router;

import com.binninfo.model_train.service.event.task.IModelTaskStepEventHandler;
import com.binninfo.model_train.service.event.task.ModelTaskStepEvent;

/**
 * 模型任务事件断言接口
 */
@FunctionalInterface
public interface ModelTaskEventPredicate {
    /**
     * 断言方法
     * @param event 事件
     * @param handler 模型任务处理器
     * @return boolean
     */
    boolean evaluate(ModelTaskStepEvent event, IModelTaskStepEventHandler handler);
}
