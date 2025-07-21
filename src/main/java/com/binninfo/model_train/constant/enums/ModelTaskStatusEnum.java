package com.binninfo.model_train.constant.enums;

import com.binninfo.excelcommon.enumtranslate.eunm.TranslateEnumInterface;

/**
 * @Author chengang
 * @Date 2025/5/27
 */
public enum ModelTaskStatusEnum implements TranslateEnumInterface {
    /**
     * 模型训练任务状态枚举
     */
    UN_START(0,"未开始"),
    START(1,"进行中"),
    SUCCEED(2,"已完成"),
    FAIL(-1,"失败")
    ;

    private Integer code;
    private String description;

    ModelTaskStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ModelTaskStatusEnum find(Integer code){
        for (ModelTaskStatusEnum value : ModelTaskStatusEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        return FAIL;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return description;
    }

    public String getDescription() {
        return description;
    }
}
