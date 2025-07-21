package com.binninfo.model_train.service.stepactuator;

/**
 * @Author chengang
 * @Date 2025/7/9
 */
public interface IProgressType extends ITypeCode{
    default Integer getStepCode(){
        return this.getTypeCode();
    }

    String getStepName();
}
