package com.binninfo.model_train.service.cache;

import com.binninfo.model_train.bean.ModelTask;

/**
 * @Author chengang
 * @Date 2025/5/29
 */
public class ModelTaskThreadLocal extends CommonThreadLocal<ModelTask>{
    private static final ModelTaskThreadLocal CONTENT = new ModelTaskThreadLocal();

    public static ModelTaskThreadLocal getContent(){
        return CONTENT;
    }
}
