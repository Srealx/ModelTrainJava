package com.binninfo.model_train.service.stepactuator.yolo;

import com.binninfo.model_train.annotation.ModelTaskStep;
import com.binninfo.model_train.constant.bus.TaskTypeConstant;
import com.binninfo.model_train.constant.bus.YoloModelTaskStepConstant;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.YoloModelTaskStepEnum;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import com.binninfo.model_train.vo.dto.YoloTaskStepExecuteDTO;
import lombok.extern.slf4j.Slf4j;
/**
 * json转换步骤执行器
 * @Author chengang
 * @Date 2025/6/18
 */
@Slf4j
@ModelTaskStep(model = ModelEnum.YOLO, taskTypeCode= TaskTypeConstant.ARBITRARY, stepNumber = YoloModelTaskStepConstant.CONVERT_JSON_CODE)
public class YoloConvertJsonActuator extends YoloModelTaskStepStepActuator {

    @Override
    public YoloTaskStepExecuteDTO buildParam(TaskStepExecuteDTO taskStepExecuteDTO){
        // 构建该节点的调用数据
        YoloTaskStepExecuteDTO yoloTaskStepExecuteDTO = super.buildParam(taskStepExecuteDTO);
        return yoloTaskStepExecuteDTO;
    }

    @Override
    public TaskStepResultDTO execute(YoloTaskStepExecuteDTO taskStepExecuteDTO) {
        // 构建请求数据, 发送任务 (http, mq产生消息等)
        return executeRequest(result, modelTask.getFileUuid());
    }

    @Override
    public YoloModelTaskStepEnum getStep() {
        return YoloModelTaskStepEnum.CONVERT_JSON;
    }
}
