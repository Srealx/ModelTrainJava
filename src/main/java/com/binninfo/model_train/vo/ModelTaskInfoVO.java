package com.binninfo.model_train.vo;

import com.binninfo.excelcommon.enumtranslate.TransformUtil;
import com.binninfo.excelcommon.enumtranslate.annotation.EnumTranslate;
import com.binninfo.model_train.constant.DateConstant;
import com.binninfo.model_train.constant.enums.ModelTaskStatusEnum;
import com.binninfo.model_train.vo.dto.event.ModelBusEventArgsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.assertj.core.util.Lists;

import java.util.Date;
import java.util.List;

/**
 * 模型训练详情数据vo
 * @Author chengang
 * @Date 2025/5/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelTaskInfoVO extends ModelBusEventArgsDTO {
    /**
     * 任务id
     */
    private Integer id;
    @JsonIgnore
    private String model;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    @EnumTranslate(codeField="codeField",enumClass = ModelTaskStatusEnum.class)
    private String taskStatusString;
    /**
     * 创建人
     */
    private Integer createUserId;
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimeString;
    /**
     * 训练模型
     */
    private String modelName;

    /**
     * 数据大小
     */
    private Long dataSize;
    private String dataSizeString;
    /**
     * 数据量
     */
    private Integer dataCount;
    /**
     * 数据量
     */
    private Integer packDataCount;


    public ModelTaskInfoVO translate(){
        List<ModelTaskInfoVO> list = Lists.newArrayList();
        list.add(this);
        TransformUtil.translate(list);
        if (this.createTime!=null){
            this.setCreateTimeString(DateConstant.STANDARD_SIMPLE_DATE_FORMAT.format(this.createTime));
        }
        if (this.dataSize!=null){
            this.setDataSizeString(String.format ("%.4f",this.dataSize / 1073741824f) +"GB");
        }
        return this;
    }


}
