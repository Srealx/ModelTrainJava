package com.binninfo.model_train.service.event.bus;

import com.binninfo.model_train.vo.dto.event.ExtractUploadDataPackEventArgsDTO;
import lombok.Getter;

/**
 * 模型事件-解压用户上传的压缩包
 * @Author chengang
 * @Date 2025/7/7
 */
@Getter
public class ExtractUploadDataPackEvent extends ModelBusEvent<ExtractUploadDataPackEventArgsDTO> {

    ExtractUploadDataPackEventArgsDTO extractUploadDataPackEventArgsDTO;

    public ExtractUploadDataPackEvent(Object source, ExtractUploadDataPackEventArgsDTO extractUploadDataPackEventArgsDTO) {
        super(source,extractUploadDataPackEventArgsDTO);
        this.extractUploadDataPackEventArgsDTO = extractUploadDataPackEventArgsDTO;
    }
}
