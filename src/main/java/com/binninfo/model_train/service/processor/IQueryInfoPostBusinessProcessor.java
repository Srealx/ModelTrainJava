package com.binninfo.model_train.service.processor;

import java.util.List;

/**
 * 查询详情后置处理器
 * @Author chengang
 * @Date 2025/5/29
 */
public interface IQueryInfoPostBusinessProcessor<T> extends IPostBusinessProcessor{

    /**
     * 查询详情后置处理器
     * @param info info
     * @return {@link List<T>}
     */
    default T queryInfoProcessor(T info){
        return info;
    }

}
