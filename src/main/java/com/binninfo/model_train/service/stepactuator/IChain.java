package com.binninfo.model_train.service.stepactuator;

/**
 * 链
 * @Author chengang
 * @Date 2025/6/18
 */
public interface IChain <N extends IChain<N>>{
    N getNextNode();
}
