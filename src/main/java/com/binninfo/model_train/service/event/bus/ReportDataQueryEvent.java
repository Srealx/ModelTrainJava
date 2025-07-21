package com.binninfo.model_train.service.event.bus;

import com.binninfo.model_train.vo.ModelTaskReportVO;
import lombok.Getter;

/**
 * @Author chengang
 * @Date 2025/7/11
 */
@Getter
public class ReportDataQueryEvent extends ModelBusEvent<ModelTaskReportVO>{

    public ReportDataQueryEvent(Object source, ModelTaskReportVO modelTaskReportVO) {
        super(source, modelTaskReportVO);
    }

    public void setModelTrainTaskReportVO(ModelTaskReportVO modelTaskReportVO){
        super.setModelBusEventArgsDTO(modelTaskReportVO);
    }

}
