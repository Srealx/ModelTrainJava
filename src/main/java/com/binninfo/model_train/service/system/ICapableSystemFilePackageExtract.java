package com.binninfo.model_train.service.system;

import com.binninfo.model_train.exception.CustomException;
import com.binninfo.model_train.service.system.capacity.IPackageExtractCapable;

import java.io.File;
import java.util.Map;
import java.util.function.Function;

/**
 * 有解压能力的操作系统压缩包解压接口
 * @Author chengang
 * @Date 2025/5/12
 */
public interface ICapableSystemFilePackageExtract extends ISystemFilePackageExtract, IPackageExtractCapable<File,String> {

    @Override
    default String extract(File file) {
        String fileName = file.getName();
        String[] split = fileName.split("\\.");
        Function<File,String> iPackageExtract = getFilePackageExtractMap().get(split[split.length - 1]);
        if (iPackageExtract == null){
            throw new CustomException("文件压缩包解压失败, 不支持的解压格式");
        }
        return iPackageExtract.apply(file);
    }

    /**
     * 解压压缩包
     * @param packageExtractDTO 文件
     */
    @Override
    default String extractFilePackage(File packageExtractDTO){
        return this.extract(packageExtractDTO);
    }

    /**
     * 获取对应的压缩包格式
     * @return {@link Map}
     */
    Map<String, Function<File,String>> getFilePackageExtractMap();

}
