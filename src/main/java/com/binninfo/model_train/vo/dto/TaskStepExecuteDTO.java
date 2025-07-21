package com.binninfo.model_train.vo.dto;

import com.binninfo.model_train.bean.ModelTask;
import com.binninfo.model_train.bean.YoloModelTaskInfo;
import com.binninfo.model_train.config.storage.StorageDirectoryConfig;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 任务结果dto
 * @Author chengang
 * @Date 2025/6/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TaskStepExecuteDTO {
    /**
     * python服务配置
     */
    PythonInvokeConfig pythonInvokeConfig;
    /**
     * 存储文件夹配置
     */
    StorageDirectoryConfig storageDirectoryConfig;
    /**
     * 模型任务基础数据
     */
    ModelTask modelTask;
    /**
     * 模型任务的详情数据
     */
    YoloModelTaskInfo yoloModelTaskInfo;
    /**
     * 模型版本数据
     */
    TaskModelDtaDTO taskModelDtaDTO;
    /**
     * 任务类型
     */
    ModelTaskTypeEnum modelTaskTypeEnum;

}
