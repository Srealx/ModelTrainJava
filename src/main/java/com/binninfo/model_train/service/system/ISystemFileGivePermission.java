package com.binninfo.model_train.service.system;

import com.binninfo.model_train.service.system.capacity.IFileGivePermissionCapable;

import java.io.File;


/**
 * 有解压能力的操作系统压缩包解压接口
 * @Author chengang
 * @Date 2025/5/12
 */
public interface ISystemFileGivePermission extends ISystemOperate, IFileGivePermissionCapable<File> {

}
