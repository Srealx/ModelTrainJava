package com.binninfo.model_train.vo.models.yolo;

import com.binninfo.model_train.vo.ModelTaskInfoVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 模型训练详情数据vo
 * @Author chengang
 * @Date 2025/5/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class YoloModelTaskInfoVO extends ModelTaskInfoVO {
    /**
     * 标注数
     */
    private Integer markCount;
    /**
     * 标签数量
     */
    private Integer labelCount;
    /**
     * 标签详情列表
     */
    private List<? extends YoloModelTaskInfoLabelVO> labelList;

    @Override
    public YoloModelTaskInfoVO translate(){
        super.translate();
        if (this.labelCount == null){
            this.labelCount = 0;
        }
        if (this.markCount == null){
            this.markCount = 0;
        }
        return this;
    }

}
