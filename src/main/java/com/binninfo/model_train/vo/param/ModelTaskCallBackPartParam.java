package com.binninfo.model_train.vo.param;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * 模型任务回调param
 * @Author chengang
 * @Date 2025/6/27
 */
@Data
@ToString
public class ModelTaskCallBackPartParam extends ModelTaskCallBackParam{
    /**
     * 文件
     */
    private MultipartFile zipFile;
    private String zipFileName;

    /**
     * formData接收体需要字符串来接收json数据
     */
    private String resultString;
}
