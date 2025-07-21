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
 * 标签提取步骤执行器
 * @Author chengang
 * @Date 2025/6/18
 */
@Slf4j
@ModelTaskStep(model = ModelEnum.YOLO, taskTypeCode= TaskTypeConstant.ARBITRARY,stepNumber = YoloModelTaskStepConstant.EXTRACT_LABELS_CODE)
public class YoloExtractLabelsActuator extends YoloCallBackModelTaskStepActuatorStep<ModelTaskCallBackParam> {

    @Override
    public TaskStepResultDTO execute(YoloTaskStepExecuteDTO taskStepExecuteDTO) {
        // 构建请求数据, 发送任务 (http, mq产生消息等)
        return executeRequest(result, modelTask.getFileUuid());
    }

    @Override
    public YoloModelTaskStepEnum getStep() {
        return YoloModelTaskStepEnum.EXTRACT_LABELS;
    }

    @Override
    public void handlerCallBack(ModelTaskCallBackParam modelTaskCallBackParam) {
        // 回调处理 更新labels等
    }
}
