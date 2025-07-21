package com.binninfo.model_train.service.impl;

import com.binninfo.model_train.service.IFragmentUploadService;

import com.binninfo.model_train.vo.FileFragmentUploadVO;
import com.binninfo.model_train.vo.param.FileFragmentUploadParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Author Srealx
 * @Date 2025/7/14
 */
@Service
@Slf4j
public class FileServiceImpl implements IFragmentUploadService {

    /**
     * 文件分片上传
     * @param file 文件
     * @param param 分片参数
     */
    @Override
    public FileFragmentUploadVO fragmentUpload(MultipartFile file, FileFragmentUploadParam param) {
        // 业务逻辑-分片上传
        // 使用锁限制分片上传任务的单通道， 如 redisson 锁
        return null;
    }
}
