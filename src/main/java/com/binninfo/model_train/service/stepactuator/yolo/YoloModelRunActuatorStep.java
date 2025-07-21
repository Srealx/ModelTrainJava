package com.binninfo.model_train.service.stepactuator.yolo;

import com.binninfo.model_train.annotation.ModelTaskStep;

import com.binninfo.model_train.constant.bus.TaskTypeConstant;
import com.binninfo.model_train.constant.bus.YoloModelTaskStepConstant;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.YoloModelTaskStepEnum;
import com.binninfo.model_train.service.event.task.BeforeCallbackReturnStepEvent;
import com.binninfo.model_train.vo.dto.*;
import com.binninfo.model_train.vo.param.ModelTaskCallBackPartParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 执行模型-步骤执行器
 * @Author chengang
 * @Date 2025/6/18
 */
@Slf4j
@ModelTaskStep(model = ModelEnum.YOLO, taskTypeCode= TaskTypeConstant.ARBITRARY,stepNumber = YoloModelTaskStepConstant.RUN_MODEL_CODE)
public class YoloModelRunActuatorStep extends YoloCallBackModelTaskStepActuatorStep<ModelTaskCallBackPartParam> {

    public YoloModelRunActuatorStep(ModelVersionMapper modelVersionMapper, StorageDirectoryConfig storageDirectoryConfig){
        // 添加事件处理器， 注册步骤相关的事件处理
        this.addModelTaskEventHandler(BeforeCallbackReturnStepEvent.class, event->handleBeforeCallbackReturnEvent((BeforeCallbackReturnStepEvent)event));
    }

    @Override
    public YoloModelTaskStepEnum getStep() {
        return YoloModelTaskStepEnum.RUN_MODEL;
    }

    private void handleBeforeCallbackReturnEvent(BeforeCallbackReturnStepEvent event) {
        // 处理事件-回调请求初始阶段, 将http请求的数据包进行实例化
    }

    @Override
    public YoloTaskStepExecuteDTO buildParam(TaskStepExecuteDTO taskStepExecuteDTO){
        // 构建节点处理参数
        YoloTaskStepExecuteDTO yoloTaskStepExecuteDTO = super.buildParam(taskStepExecuteDTO);
        return yoloTaskStepExecuteDTO;
    }

    @Override
    public TaskStepResultDTO execute(YoloTaskStepExecuteDTO taskStepExecuteDTO) {
        // 构建请求数据, 发送任务 (http, mq产生消息等)
        return executeRequest(result, modelTask.getFileUuid());
    }

    @Override
    public void handlerCallBack(ModelTaskCallBackPartParam modelTaskCallBackParam) {
        // 回调处理： 解析结果，入库，解析结果文件，存储相关运行文件等
    }
}
