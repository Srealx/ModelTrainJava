package com.binninfo.model_train.service.system;

import java.io.File;

/**
 * 操作系统压缩包解压接口
 * @Author chengang
 * @Date 2025/5/12
 */
public interface ISystemFilePackageExtract extends ISystemOperate{
    /**
     * 解压压缩包
     * @param file 文件
     */
    String extractFilePackage(File file);
}
