package com.binninfo.model_train.vo;

import lombok.Data;

/**
 * 模型任务结果文件下载vo
 * @Author chengang
 * @Date 2025/7/17
 */
@Data
public class ModelTaskResultFileDownloadVO {
    /**
     * 下载链接
     */
    private String downLoadUrl;
    /**
     * 文件名
     */
    private String fileName;
}
