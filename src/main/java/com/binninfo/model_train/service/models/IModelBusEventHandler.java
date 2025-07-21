package com.binninfo.model_train.service.models;

import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.service.event.bus.*;
import com.binninfo.model_train.vo.dto.event.ModelTaskResultDownloadUrlGetDTO;

/**
 * 模型业务-事件处理器
 * @Author chengang
 * @Date 2025/7/7
 */
public interface IModelBusEventHandler {

    ModelEnum getModel();

    /**
     * 处理解压用户上传的压缩包
     */
    void handlerExtractUploadDataPack(ExtractUploadDataPackEvent extractUploadDataPackEvent);

    /**
     * 处理模型任务新增
     * @param modelTaskAddEvent {@link ModelTaskAddEvent}
     */
    void handlerModelTaskAdd(ModelTaskAddEvent modelTaskAddEvent);

    /**
     * 处理列表查询事件
     */
    void handlerQueryList(ModelTaskListQueryEvent modelTaskListQueryEvent);

    /**
     * 处理查询模型任务详情
     * @param yoloModelTaskInfoEvent {@link YoloModelTaskInfoEvent}
     */
    void handlerQueryModelTaskInfo(YoloModelTaskInfoEvent yoloModelTaskInfoEvent);

    /**
     * 处理查询模型任务执行报表数据
     */
    void handlerReportDataQuery(ReportDataQueryEvent reportDataQueryEvent);

    /**
     * 处理模型详情查询
     * @param modelInfoQueryEvent {@link ModelInfoQueryEvent}
     */
    void handlerModelInfoQuery(ModelInfoQueryEvent modelInfoQueryEvent);

    /**
     * 处理查询模型任务结果模型下载链接
     * @param modelTaskResultDownloadUrlGetDTO {@link ModelTaskResultDownloadUrlGetDTO}
     */
    void handlerModelTaskResultDownloadUrlGet(ModelTaskResultDownloadUrlGetEvent modelTaskResultDownloadUrlGetDTO);
}
