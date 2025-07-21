package com.binninfo.model_train.service.event.router;

import cn.hutool.core.collection.CollUtil;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.event.bus.ModelBusEvent;
import com.binninfo.model_train.service.event.task.IModelTaskStepEventHandler;
import com.binninfo.model_train.service.event.task.ModelTaskStepEvent;
import com.binninfo.model_train.service.models.IModelTaskTypeBusEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 模型任务事件路由器
 * @Author chengang
 * @Date 2025/7/4
 */
@Component
@Slf4j
public class ModelEventRouter implements ApplicationRunner {
    @Resource
    List<IModelTaskStepEventHandler> modelTaskEventHandlerList;
    @Resource
    ModelTaskEventPredicate modelTaskEventPredicate;
    @Resource
    List<IModelTaskTypeBusEventHandler> modelBusEventHandlerList;
    Map<ModelEnum,Map<ModelTaskTypeEnum,IModelTaskTypeBusEventHandler>> modelBusEventHandlerMap;
    Map<ModelEnum,Map<ModelTaskTypeEnum,Map<Class<? extends ModelBusEvent<?>>, Method>>> modelEventMethodMap;

    @EventListener
    public <E extends ModelBusEvent<?>> void handleModelBusEvent(E event) {
        ModelEnum modelEnum = event.getModelBusEventArgsDTO().getModelEnum();
        ModelTaskTypeEnum modelTaskTypeEnum = event.getModelBusEventArgsDTO().getModelTaskTypeEnum();
        try {
            modelEventMethodMap.get(modelEnum).get(modelTaskTypeEnum).get(event.getClass()).invoke(modelBusEventHandlerMap.get(modelEnum).get(modelTaskTypeEnum),event);
        } catch (Exception e) {
            log.error("事件处理: handleModelBusEvent 执行失败, 失败原因", e);
            throw new RuntimeException("系统异常");
        }
    }

    @EventListener
    public <E extends ModelTaskStepEvent> void handleModelTaskEvent(E event) {
        for (IModelTaskStepEventHandler handler : modelTaskEventHandlerList) {
            if (modelTaskEventPredicate.evaluate(event,handler)){
                Consumer<ModelTaskStepEvent> eventConsumer = handler.getHandlerEvents().get(event.getClass());
                if (eventConsumer != null){
                    eventConsumer.accept(event);
                    break;
                }
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (CollUtil.isNotEmpty(modelBusEventHandlerList)){
            modelEventMethodMap = modelBusEventHandlerList.stream().collect(Collectors.groupingBy(IModelTaskTypeBusEventHandler::getModel,Collectors.toMap(IModelTaskTypeBusEventHandler::getModelTaskType,item->
                    Arrays.stream(item.getClass().getMethods()).filter(method -> method.getParameterTypes().length == 1 && ModelBusEvent.class.isAssignableFrom(method.getParameterTypes()[0]))
                            .collect(Collectors.toMap(method ->{
                                @SuppressWarnings("unchecked")
                                Class<? extends ModelBusEvent<?>> modelTaskEventClass = (Class<? extends ModelBusEvent<?>>) method.getParameterTypes()[0];
                                return modelTaskEventClass;
                            }, Function.identity())))));

            modelBusEventHandlerMap = modelBusEventHandlerList.stream()
                    .collect(Collectors.groupingBy(IModelTaskTypeBusEventHandler::getModel,Collectors.toMap(IModelTaskTypeBusEventHandler::getModelTaskType,Function.identity())));
        }
    }
}
