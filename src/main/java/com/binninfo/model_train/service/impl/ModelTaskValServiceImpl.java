package com.binninfo.model_train.service.impl;

import com.binninfo.model_train.bean.ModelTask;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.event.bus.ModelTaskListQueryEvent;
import com.binninfo.model_train.service.event.bus.YoloModelTaskInfoEvent;
import com.binninfo.model_train.service.factory.ModelTaskStepActuatorFactory;
import com.binninfo.model_train.service.stepactuator.IChainedTaskStepActuator;
import com.binninfo.model_train.vo.*;
import com.binninfo.model_train.vo.dto.event.ModelTaskListEventDTO;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import com.binninfo.model_train.vo.param.ModelTaskAddParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模型训练任务-测试任务服务
 * @Author chengang
 * @Date 2025/5/12
 */
@Service
@Slf4j
public class ModelTaskValServiceImpl extends ModelTaskBaseServiceImpl {
    @Resource
    ModelTaskStepActuatorFactory modelTaskStepActuatorFactory;

    @Override
    public ModelTaskTypeEnum getTaskType() {
        return ModelTaskTypeEnum.VAL;
    }

    @Override
    public void addEntityProcessor(Object modelTrainTaskAddParam, ModelTask entity){
        afterAddPublishEvent((ModelTaskAddParam)modelTrainTaskAddParam,entity);
    }

    @Override
    public List<ModelTaskListVO> listPostProcessor(List<ModelTaskListVO> list,ModelEnum modelEnum){
        ModelTaskListEventDTO modelTaskListEventDTO = new ModelTaskListEventDTO(modelEnum,this.getTaskType(), list);
        ModelTaskListQueryEvent event = new ModelTaskListQueryEvent(this,modelTaskListEventDTO);
        applicationEventPublisher.publishEvent(event);
        return event.getModelBusEventArgsDTO().getList();
    }

    @Override
    public ModelTaskInfoVO queryInfoProcessor(ModelTaskInfoVO info){
        info.setModelTaskTypeEnum(this.getTaskType());
        info.setModelEnum(ModelEnum.findByName(info.getModel()));
        YoloModelTaskInfoEvent yoloModelTaskInfoEvent = new YoloModelTaskInfoEvent(this, info);
        applicationEventPublisher.publishEvent(yoloModelTaskInfoEvent);
        return yoloModelTaskInfoEvent.getModelBusEventArgsDTO();
    }

    @Override
    public IChainedTaskStepActuator<TaskStepResultDTO, TaskStepExecuteDTO> getStepActuator(ModelEnum modelEnum) {
        return modelTaskStepActuatorFactory.getStartNode(modelEnum,this.getTaskType());
    }

}
