package com.binninfo.model_train.service.delay;

/**
 * 延时处理接口
 * @Author chengang
 * @Date 2025/7/9
 */
public interface IDelayHandler {
    /**
     * 处理延时key
     * @param key key
     */
    void handlerDelay(String key);

    /**
     * 获取处理器标识
     * @return String
     */
    String getSign();
}
