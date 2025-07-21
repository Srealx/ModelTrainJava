package com.binninfo.model_train.vo.dto.event;

import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 解压用户上传的数据文件参数
 * @Author chengang
 * @Date 2025/7/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExtractUploadDataPackEventArgsDTO extends ModelBusEventArgsDTO {
    public ExtractUploadDataPackEventArgsDTO(ModelEnum modelEnum,String uuid,String folderPath,ModelTaskTypeEnum modelTaskTypeEnum) {
        super(modelEnum,modelTaskTypeEnum);
        this.uuid = uuid;
        this.folderPath = folderPath;
    }

    private String uuid;

    /**
     * 解压后的文件夹完整路径
     */
    String folderPath;
}
