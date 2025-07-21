package com.binninfo.model_train.service.system.capacity;

import java.io.File;


/**
 * 压缩包解压能力接口
 * @Author chengang
 * @Date 2025/5/12
 */
public interface IPackageExtractCapable<P extends File,R> extends IFileOperateCapable<P>{
    /**
     * 解压文件
     * @param file 文件
     */
    R extract(P file);
}
