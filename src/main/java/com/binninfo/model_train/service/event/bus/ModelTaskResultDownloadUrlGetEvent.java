package com.binninfo.model_train.service.event.bus;

import com.binninfo.model_train.vo.dto.event.ModelTaskResultDownloadUrlGetDTO;

/**
 * @Author chengang
 * @Date 2025/7/14
 */
public class ModelTaskResultDownloadUrlGetEvent extends ModelBusEvent<ModelTaskResultDownloadUrlGetDTO>{

    public ModelTaskResultDownloadUrlGetEvent(Object source, ModelTaskResultDownloadUrlGetDTO modelBusEventArgsDTO) {
        super(source, modelBusEventArgsDTO);
    }
}
