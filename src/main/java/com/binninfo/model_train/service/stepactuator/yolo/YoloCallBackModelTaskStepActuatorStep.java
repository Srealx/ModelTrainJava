package com.binninfo.model_train.service.stepactuator.yolo;

import com.binninfo.model_train.service.stepactuator.ICallBackHandler;
import com.binninfo.model_train.vo.param.ModelTaskCallBackParam;

/**
 * yolo 任务步骤-有回调处理的
 * @Author chengang
 * @Date 2025/7/1
 */
public abstract class YoloCallBackModelTaskStepActuatorStep<D extends ModelTaskCallBackParam> extends YoloModelTaskStepStepActuator implements ICallBackHandler<D> {

}
