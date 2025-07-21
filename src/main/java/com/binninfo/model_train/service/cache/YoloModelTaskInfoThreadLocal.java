package com.binninfo.model_train.service.cache;

import com.binninfo.model_train.bean.YoloModelTaskInfo;

/**
 * @Author chengang
 * @Date 2025/7/14
 */
public class YoloModelTaskInfoThreadLocal extends CommonThreadLocal<YoloModelTaskInfo>{
    private static final YoloModelTaskInfoThreadLocal CONTENT = new YoloModelTaskInfoThreadLocal();

    public static YoloModelTaskInfoThreadLocal getContent(){
        return CONTENT;
    }
}
