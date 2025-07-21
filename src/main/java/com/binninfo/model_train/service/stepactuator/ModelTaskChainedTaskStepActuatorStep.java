package com.binninfo.model_train.service.stepactuator;

import com.binninfo.model_train.service.event.task.IModelTaskStepEventHandler;
import com.binninfo.model_train.service.event.task.ModelTaskStepEvent;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 模型任务步骤处理链
 * @Author chengang
 * @Date 2025/7/3
 */
@Slf4j
@Setter
public abstract class ModelTaskChainedTaskStepActuatorStep<R extends TaskStepResultDTO, P extends TaskStepExecuteDTO> extends ConfigurableChainedTaskStepActuator<R,P> implements IModelTaskStepEventHandler {


    Map<Class<? extends ModelTaskStepEvent> , Consumer<ModelTaskStepEvent>> modelTaskEventMap = new HashMap<>();

    /**
     * 获取存放模型文件的子文件夹
     * @return String
     */
    public abstract String getModelSubFolder();

    /**
     * 获取模型名称
     * @return String
     */
    public abstract String model();

    @Override
    public Map<Class<? extends ModelTaskStepEvent> , Consumer<ModelTaskStepEvent>> getHandlerEvents() {
        return this.modelTaskEventMap;
    }

    public void addModelTaskEventHandler(Class<? extends ModelTaskStepEvent> eventClass , Consumer<ModelTaskStepEvent> consumer){
        this.modelTaskEventMap.put(eventClass,consumer);
    }

    /**
     * 存储模型文件
     * @param model 模型文件
     * @param modelName 模型文件名称
     * @param version 版本号
     * @param suffix 后缀,代表目标文件类型
     */
    protected void modelStorage(Integer modelTaskId,File model, String modelName,Integer version, String suffix){

    }
}
