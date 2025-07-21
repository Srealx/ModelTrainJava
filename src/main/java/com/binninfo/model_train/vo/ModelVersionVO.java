package com.binninfo.model_train.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author chengang
 * @Date 2025/6/5
 */
@Data
public class ModelVersionVO {
    /**
     * 版本号
     */
    private String version;
    /**
     * 格式后缀
     */
    private String suffix;

    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimeString;

    /**
     * 下载链接
     */
    private String downloadUrl;
}
