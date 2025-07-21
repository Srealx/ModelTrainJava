package com.binninfo.model_train.service.factory;

import com.binninfo.model_train.annotation.ModelTaskStep;
import com.binninfo.model_train.constant.bus.TaskTypeConstant;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.stepactuator.ITypeCode;
import com.binninfo.model_train.service.stepactuator.ModelTaskChainedTaskStepActuatorStep;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务bean配置
 * @Author chengang
 * @Date 2025/7/1
 */
@Configuration
public class ServiceBeanConfig {

    @Bean
    @Qualifier("specificActuators")
    public Map<ModelEnum,Map<ITypeCode,List<ModelTaskChainedTaskStepActuatorStep>>> specificActuators(List<ModelTaskChainedTaskStepActuatorStep> list){
        Map<ModelEnum, List<ModelTaskChainedTaskStepActuatorStep>> collect = list.stream()
                .filter(item -> item.getClass().getAnnotation(ModelTaskStep.class) != null)
                .collect(Collectors.groupingBy(item -> item.getClass().getAnnotation(ModelTaskStep.class).model()));

        Map<ModelEnum,Map<ITypeCode,List<ModelTaskChainedTaskStepActuatorStep>>> reMap = new HashMap<>();
        Set<Integer> allTypeSet = TaskTypeConstant.allTypes();
        collect.forEach((k,v)->{
            Map<ITypeCode,List<ModelTaskChainedTaskStepActuatorStep>> typeMap = new HashMap<>();
            Map<Integer,ITypeCode> iTypeCodeMap = new HashMap<>();
            allTypeSet.forEach(item->{
                ITypeCode typeCode = ModelTaskTypeEnum.find(item);
                iTypeCodeMap.put(typeCode.getTypeCode(),typeCode);
                typeMap.put(typeCode,Lists.newArrayList());
            });
            v = v.stream().sorted(Comparator.comparingInt(o -> o.getClass().getAnnotation(ModelTaskStep.class).stepNumber())).collect(Collectors.toList());
            v.forEach(item->{
                ModelTaskStep annotation = item.getClass().getAnnotation(ModelTaskStep.class);
                int typeCode = annotation.taskTypeCode();
                if (TaskTypeConstant.ARBITRARY == typeCode){
                    typeMap.forEach((k2,v2)->v2.add(item));
                }else {
                    typeMap.get(iTypeCodeMap.get(typeCode)).add(item);
                }
            });
            reMap.put(k,typeMap);
        });
        return reMap;
    }

}
