package com.binninfo.model_train.service.processor;

import com.binninfo.model_train.constant.enums.ModelEnum;

import java.util.List;

/**
 * 列表查询业务后置处理器
 * @Author chengang
 * @Date 2025/5/29
 */
public interface IListQueryPostBusinessProcessor<T> extends IPostBusinessProcessor{

    /**
     * 列表后置处理
     * @param list list
     * @param modelEnum {@link ModelEnum}
     * @return {@link List<T>}
     */
    default List<T> listPostProcessor(List<T> list, ModelEnum modelEnum){
        return list;
    }

}
