package com.binninfo.model_train.constant.enums;

import com.binninfo.model_train.service.stepactuator.IProgressType;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @Author chengang
 * @Date 2025/6/30
 */
@Getter
public enum ModelEnum {
    /**
     * 系统支持模型枚举
     */
    YOLO("yolo", "yolo", YoloModelTaskStepEnum::findByCode, YoloModelTaskStepEnum.progressTypeList()),
    ;

    private final String name;
    private final String modelFolder;
    private final Function<Integer, IProgressType> stepFindFunc;
    private final List<IProgressType> progressTypeList;

    ModelEnum(String name, String modelFolder,Function<Integer, IProgressType> stepFindFunc,List<IProgressType> progressTypeList) {
        this.name = name;
        this.modelFolder = modelFolder;
        this.stepFindFunc = stepFindFunc;
        this.progressTypeList = progressTypeList;
    }

    public static ModelEnum findByName(String name){
        for (ModelEnum value : ModelEnum.values()) {
            if(Objects.equals(value.name, name)){
                return value;
            }
        }
        return ModelEnum.YOLO;
    }

    public IProgressType getStepDesc(Integer code){
       return this.stepFindFunc.apply(code);
    }

}
