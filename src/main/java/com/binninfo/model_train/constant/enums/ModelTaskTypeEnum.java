package com.binninfo.model_train.constant.enums;

import com.binninfo.model_train.constant.bus.TaskTypeConstant;
import com.binninfo.model_train.service.stepactuator.ITypeCode;
import lombok.Getter;

/**
 * @Author chengang
 * @Date 2025/5/12
 */
@Getter
public enum ModelTaskTypeEnum implements ITypeCode {
    /**
     * 模型训练类型枚举
     */
    VAL(TaskTypeConstant.VAL,"评估测试","val"),
    TRAIN(TaskTypeConstant.TRAIN,"训练","train"),
    ;

    public final Integer typeCode;
    private final String desc;
    private final String folderName;

    @Override
    public Integer getTypeCode() {
        return typeCode;
    }

    ModelTaskTypeEnum(Integer typeCode, String desc, String folderName) {
        this.typeCode = typeCode;
        this.desc = desc;
        this.folderName = folderName;
    }

    public static ModelTaskTypeEnum find(Integer typeCode){
        for (ModelTaskTypeEnum value : ModelTaskTypeEnum.values()) {
            if (value.typeCode.equals(typeCode)){
                return value;
            }
        }
        return null;
    }

    public static ModelTaskTypeEnum find(Boolean testFlag){
        return find(Boolean.TRUE.equals(testFlag)?1:0);
    }
}
