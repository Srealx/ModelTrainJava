package com.binninfo.model_train.service.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @Author chengang
 * @Date 2025/7/4
 */
public interface IEventHandler<T extends ApplicationEvent>{
    Map<Class<? extends T>,Consumer<T>> getHandlerEvents();
}
