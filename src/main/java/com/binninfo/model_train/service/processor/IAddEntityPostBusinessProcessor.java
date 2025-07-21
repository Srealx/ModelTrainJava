package com.binninfo.model_train.service.processor;

import java.util.List;

/**
 * 新增实例后置处理器
 * @Author chengang
 * @Date 2025/5/29
 */
public interface IAddEntityPostBusinessProcessor<T> extends IPostBusinessProcessor{

    /**
     * 新增实例后置处理
     * @param entity entity
     * @return {@link List<T>}
     */
    default void addEntityProcessor(Object param,T entity){

    }


}
