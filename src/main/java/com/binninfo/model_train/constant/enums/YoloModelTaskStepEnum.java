package com.binninfo.model_train.constant.enums;

import com.binninfo.excelcommon.enumtranslate.eunm.TranslateEnumInterface;
import com.binninfo.model_train.constant.bus.ModelTaskStepConstant;
import com.binninfo.model_train.constant.bus.YoloModelTaskStepConstant;
import com.binninfo.model_train.service.stepactuator.IProgressType;
import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * yolo模型训练-步骤枚举
 * @Author chengang
 * @Date 2025/6/17
 */
public enum YoloModelTaskStepEnum implements TranslateEnumInterface, IProgressType {
    /**
     *  yolo模型训练任务步骤枚举
     */
    SEND_PACK(YoloModelTaskStepConstant.SEND_PACK_CODE,"发送压缩包"),
    EXTRACT_LABELS(YoloModelTaskStepConstant.EXTRACT_LABELS_CODE,"提取标签"),
    CONVERT_JSON(YoloModelTaskStepConstant.CONVERT_JSON_CODE,"转换json并分组"),
    GENERATE_YAML(YoloModelTaskStepConstant.GENERATE_YAML_CODE,"生成yaml文件"),
    RUN_MODEL(YoloModelTaskStepConstant.RUN_MODEL_CODE,"运行 训练/测试"),
    UN_KNOW(ModelTaskStepConstant.UN_KNOW_CODE,"未知步骤")
    ;

    private Integer code;
    private String stepName;

    YoloModelTaskStepEnum(Integer code, String stepName) {
        this.code = code;
        this.stepName = stepName;
    }

    public static YoloModelTaskStepEnum findByCode(Integer code){
        for (YoloModelTaskStepEnum value : YoloModelTaskStepEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        return YoloModelTaskStepEnum.UN_KNOW;
    }

    public static List<IProgressType> progressTypeList(){
        List<IProgressType> reList = Lists.newArrayList();
        reList.addAll(Arrays.stream(YoloModelTaskStepEnum.values()).filter(item -> item != YoloModelTaskStepEnum.UN_KNOW).collect(Collectors.toList()));
        return reList;
    }

    @Override
    public int getCode(){
        return this.code;
    }

    @Override
    public Integer getTypeCode() {
        return getCode();
    }

    @Override
    public String getValue() {
        return stepName;
    }

    @Override
    public String getStepName() {
        return stepName;
    }
}
