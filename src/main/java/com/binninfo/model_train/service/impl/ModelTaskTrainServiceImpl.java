package com.binninfo.model_train.service.impl;

import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.event.bus.ModelTaskListQueryEvent;
import com.binninfo.model_train.service.event.bus.YoloModelTaskInfoEvent;
import com.binninfo.model_train.service.factory.ModelTaskStepActuatorFactory;
import com.binninfo.model_train.service.stepactuator.IChainedTaskStepActuator;
import com.binninfo.model_train.vo.ModelTaskListVO;
import com.binninfo.model_train.vo.ModelTaskInfoVO;
import com.binninfo.model_train.vo.dto.event.ModelTaskListEventDTO;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import com.binninfo.model_train.vo.param.ModelTaskAddParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模型训练任务-正式任务服务
 * @author chengang
 * @date 2025/5/12
 */
@Service("formal")
@Slf4j
public class ModelTaskTrainServiceImpl extends ModelTaskBaseServiceImpl {
    @Resource
    ModelTaskStepActuatorFactory modelTaskStepActuatorFactory;

    @Override
    public ModelTaskTypeEnum getTaskType() {
        return ModelTaskTypeEnum.TRAIN;
    }

    @Override
    public void addEntityProcessor(Object modelTrainTaskAddParam, ModelTask entity){
        afterAddPublishEvent((ModelTaskAddParam)modelTrainTaskAddParam,entity);
    }

    @Override
    public List<ModelTaskListVO> listPostProcessor(List<ModelTaskListVO> list, ModelEnum modelEnum){
        // 事件发布, 交由具体的模型进行后处理
        ModelTaskListEventDTO modelTaskListEventDTO = new ModelTaskListEventDTO(modelEnum,this.getTaskType(), list);
        ModelTaskListQueryEvent event = new ModelTaskListQueryEvent(this,modelTaskListEventDTO);
        applicationEventPublisher.publishEvent(event);
        return event.getModelBusEventArgsDTO().getList();
    }

    @Override
    public ModelTaskInfoVO queryInfoProcessor(ModelTaskInfoVO info){
        // 事件发布, 交由具体的模型进行后处理
        info.setModelEnum(ModelEnum.findByName(info.getModel()));
        info.setModelTaskTypeEnum(this.getTaskType());
        YoloModelTaskInfoEvent yoloModelTaskInfoEvent = new YoloModelTaskInfoEvent(this, info);
        applicationEventPublisher.publishEvent(yoloModelTaskInfoEvent);
        return yoloModelTaskInfoEvent.getModelBusEventArgsDTO();
    }

    @Override
    public IChainedTaskStepActuator<TaskStepResultDTO, TaskStepExecuteDTO> getStepActuator(ModelEnum modelEnum) {
        return modelTaskStepActuatorFactory.getStartNode(modelEnum,this.getTaskType());
    }
}
