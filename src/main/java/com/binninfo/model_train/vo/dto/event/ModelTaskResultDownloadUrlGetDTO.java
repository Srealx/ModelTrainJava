package com.binninfo.model_train.vo.dto.event;

import com.binninfo.model_train.bean.ModelTask;
import com.binninfo.model_train.vo.ModelTaskResultFileDownloadVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author chengang
 * @Date 2025/7/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelTaskResultDownloadUrlGetDTO extends ModelBusEventArgsDTO {
    ModelTask modelTask;
    ModelTaskResultFileDownloadVO modelTaskResultFileDownloadVO;
}
