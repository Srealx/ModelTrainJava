package com.binninfo.model_train.service.system.capacity;

import java.io.File;


/**
 * 文件提权能力接口
 * @Author chengang
 * @Date 2025/5/12
 */
public interface IFileGivePermissionCapable<P extends File> extends IFileOperateCapable<P>{
    /**
     * 文件提权
     * @param file 文件
     */
    boolean give(P file);
}
