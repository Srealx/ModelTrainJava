package com.binninfo.model_train.vo;

import lombok.Data;

/**
 * 模型训练文件上传param
 * @author chen gang
 * @date 2025/5/7
 */
@Data
public class FileFragmentUploadVO {
    /**
     * 唯一标识, 首次进行上传后后端会返, 之后的上传都需要带过来
     */
    private String uuid;
    /**
     * 合并分片是否成功
     */
    private Boolean mergeSuccessFlag;

    /**
     * 当前是第几个文件
     */
    private Integer number;
}
