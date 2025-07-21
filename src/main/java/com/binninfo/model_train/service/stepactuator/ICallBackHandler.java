package com.binninfo.model_train.service.stepactuator;

/**
 * 回调处理接口
 * @Author chengang
 * @Date 2025/7/1
 */
public interface ICallBackHandler<C> {
    /**
     * 处理回调
     * @param c C
     */
    void handlerCallBack(C c);
}
