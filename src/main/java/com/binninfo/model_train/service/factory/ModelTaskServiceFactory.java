package com.binninfo.model_train.service.factory;

/**
 * @Author chengang
 * @Date 2025/5/12
 */
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.impl.IModelTrainService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ModelTaskServiceFactory {
    private final Map<ModelTaskTypeEnum, IModelTrainService> taskServiceMap;

    public ModelTaskServiceFactory(List<IModelTrainService> taskServices) {
        taskServiceMap = taskServices.stream().collect(Collectors.toMap(item->item.getTaskType(),Function.identity()));
    }

    public IModelTrainService getService(ModelTaskTypeEnum modelTaskTypeEnum){
        if (modelTaskTypeEnum ==null){
            throw new CustomException("系统异常, modelTrainTaskTypeEnum 枚举为空");
        }
        IModelTrainService modelTrainService = taskServiceMap.get(modelTaskTypeEnum);
        if (modelTrainService == null){
            throw new NoSuchBeanDefinitionException("系统异常, 未找到taskType为"+ modelTaskTypeEnum.getTypeCode()+"的IModelTrainService实现");
        }
        return modelTrainService;
    }

    public IModelTrainService getDefaultService(){
        return taskServiceMap.values().stream().findAny().orElse(null);
    }
}
