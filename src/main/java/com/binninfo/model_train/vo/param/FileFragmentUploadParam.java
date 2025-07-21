package com.binninfo.model_train.vo.param;

import lombok.Data;

/**
 * 模型训练文件上传param
 * @author chen gang
 * @date 2025/5/7
 */
@Data
public class FileFragmentUploadParam {
    /**
     * 原始文件名称
     */
    private String originalFileName;
    /**
     * 唯一标识, 首次进行上传后后端会返, 之后的上传都需要带过来
     */
    private String uuid;
    /**
     * 原始文件哈希值(使用MD5对原始文件加密, 如果不好实现可以用下面的 originalFileSize)
     */
    private String hash;
    /**
     * 原始文件大小(如果MD5文件加密算不了,可以用这个简单一点, 单位是byte)
     */
    private Long originalFileSize;
    /**
     * 是否合并(当上传最后一个分片时传 true，后端合并分片)
     */
    private Boolean mergeFlag;
}
