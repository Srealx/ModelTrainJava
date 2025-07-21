package com.binninfo.model_train.service.cache;

import com.binninfo.model_train.bean.YoloModelTaskLabel;

import java.util.List;

/**
 * @Author chengang
 * @Date 2025/5/29
 */
public class YoloModelTaskLabelThreadLocal extends CommonThreadLocal<List<YoloModelTaskLabel>>{
    private static final YoloModelTaskLabelThreadLocal CONTENT = new YoloModelTaskLabelThreadLocal();

    public static YoloModelTaskLabelThreadLocal getContent(){
        return CONTENT;
    }
}
