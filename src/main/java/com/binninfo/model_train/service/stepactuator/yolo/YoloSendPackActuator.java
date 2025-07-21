package com.binninfo.model_train.service.stepactuator.yolo;

import com.binninfo.model_train.annotation.ModelTaskStep;
import com.binninfo.model_train.constant.bus.TaskTypeConstant;
import com.binninfo.model_train.constant.bus.YoloModelTaskStepConstant;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.YoloModelTaskStepEnum;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import com.binninfo.model_train.vo.dto.YoloTaskStepExecuteDTO;
import com.binninfo.model_train.vo.param.ModelTaskCallBackParam;
import lombok.extern.slf4j.Slf4j;

/**
 * yolo训练-发送压缩包步骤执行器
 * @Author chengang
 * @Date 2025/6/18
 */
@Slf4j
@ModelTaskStep(model = ModelEnum.YOLO, taskTypeCode= TaskTypeConstant.ARBITRARY, stepNumber = YoloModelTaskStepConstant.SEND_PACK_CODE)
public class YoloSendPackActuator extends YoloCallBackModelTaskStepActuatorStep<ModelTaskCallBackParam> {

    @Override
    public TaskStepResultDTO execute(YoloTaskStepExecuteDTO taskStepExecuteDTO) {
        // 构建请求数据, 发送任务 (http, mq产生消息等)
        return executeRequest(result, modelTask.getFileUuid());
    }

    @Override
    public YoloModelTaskStepEnum getStep() {
        return YoloModelTaskStepEnum.SEND_PACK;
    }

    @Override
    public void handlerCallBack(ModelTaskCallBackParam modelTaskCallBackParam) {
        // 节点回调处理 更新dataCount和dataSize
    }

}
