package com.binninfo.model_train.annotation;

import com.binninfo.model_train.constant.enums.ModelEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author chengang
 * @Date 2025/7/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface ModelTaskStep {
    /**
     * 所属模型
     * @return {@link ModelEnum}
     */
    ModelEnum model();

    int taskTypeCode();

    /**
     * 步骤接口的序号
     * @return {@link int}
     */
    int stepNumber();
}
