package com.binninfo.model_train.vo.dto;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @Author chengang
 * @Date 2025/5/29
 */
public class FileGivePermissionDTO extends File {
    public FileGivePermissionDTO(@NotNull String pathname) {
        super(pathname);
    }
}
