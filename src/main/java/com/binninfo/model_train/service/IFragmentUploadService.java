package com.binninfo.model_train.service;

import com.binninfo.model_train.vo.FileFragmentUploadVO;
import com.binninfo.model_train.vo.param.FileFragmentUploadParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 分片上传接口
 * @Author chengang
 * @Date 2025/5/12
 */
public interface IFragmentUploadService {
    /**
     * 分片上传压缩包
     * @param file
     * @param param
     * @return
     */
    FileFragmentUploadVO fragmentUpload(MultipartFile file, FileFragmentUploadParam param);
}
