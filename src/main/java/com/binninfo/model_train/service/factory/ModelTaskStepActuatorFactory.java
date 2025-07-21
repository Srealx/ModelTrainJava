package com.binninfo.model_train.service.factory;

import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.stepactuator.*;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 模型训练任务步骤执行器工厂
 * @Author chengang
 * @Date 2025/6/18
 */
@Component
public class ModelTaskStepActuatorFactory {
    /**
     * 模型任务-步骤map
     * 结构:   模型类型 -> 任务类型 -> 步骤 -> 对应节点
     */
    private final Map<ModelEnum,Map<ITypeCode,Map<IProgressType, ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO,TaskStepExecuteDTO>>>> STEP_MAP = new HashMap<>();
    /**
     * 模型任务-起点map
     * 结构:   模型类型 -> 任务类型 -> 对应节点
     */
    private final Map<ModelEnum, Map<ITypeCode, ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, TaskStepExecuteDTO>>> START_NODE_MAP = new HashMap<>();

    public ModelTaskStepActuatorFactory(@Qualifier("specificActuators") Map<ModelEnum,Map<ITypeCode,List<ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, TaskStepExecuteDTO>>>> stepActuatorMap){
        stepActuatorMap.forEach((model,l1)->{
            Map<ITypeCode,Map<IProgressType, ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO,TaskStepExecuteDTO>>> stepMap = new HashMap<>();
            Map<ITypeCode, ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, TaskStepExecuteDTO>> startNodeMap = new HashMap<>();
            l1.forEach((type,l2)->{
                // 对IConfigurableChainedTaskStepActuator进行节点顺序配置
                for (int i = 0; i < l2.size(); i++) {
                    // 只对末尾节点之前的配置, 末尾节点无后续节点
                    if (i != l2.size()-1){
                        l2.get(i).setNextNode(l2.get(i+1));
                    }
                }
                startNodeMap.put(type,l2.get(0));
                stepMap.put(type,l2.stream().collect(Collectors.toMap(ModelTaskChainedTaskStepActuatorStep::getStep, Function.identity())));
            });
            STEP_MAP.put(model,stepMap);
            START_NODE_MAP.put(model,startNodeMap);
        });
    }

    public ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, TaskStepExecuteDTO> getStartNode(ModelEnum modelEnum, ModelTaskTypeEnum modelTaskTypeEnum){
        return START_NODE_MAP.get(modelEnum).get(modelTaskTypeEnum);
    }

    public ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, TaskStepExecuteDTO> getNode(ModelEnum modelEnum, ModelTaskTypeEnum modelTaskTypeEnum, IProgressType stepCode){
        return STEP_MAP.get(modelEnum).get(modelTaskTypeEnum).get(stepCode);
    }

    public boolean isEndNode(ModelEnum modelEnum,ModelTaskTypeEnum modelTaskTypeEnum, IProgressType stepCode){
        return STEP_MAP.get(modelEnum).get(modelTaskTypeEnum).get(stepCode).getNextNode() == null;
    }
}
