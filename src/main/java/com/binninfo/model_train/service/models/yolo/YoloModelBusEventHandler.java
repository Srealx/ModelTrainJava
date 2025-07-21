package com.binninfo.model_train.service.models.yolo;

import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.service.event.bus.*;
import com.binninfo.model_train.service.models.IModelTaskTypeBusEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * yolo-模型事件处理
 * @Author chengang
 * @Date 2025/7/7
 */
@Slf4j
public abstract class YoloModelBusEventHandler implements IModelTaskTypeBusEventHandler {

    protected static final List<String> IMAGE_FORMAT = Lists.newArrayList();
    static {
        IMAGE_FORMAT.add("jpg");
        IMAGE_FORMAT.add("jpeg");
        IMAGE_FORMAT.add("png");
    }

    @Override
    public ModelEnum getModel() {
        return ModelEnum.YOLO;
    }

    /**
     * 处理事件-模型任务创建-解压用户上传的文件夹
     * @param extractUploadDataPackEvent {@link ExtractUploadDataPackEvent }
     */
    @Override
    public void handlerExtractUploadDataPack(ExtractUploadDataPackEvent extractUploadDataPackEvent) {
       // 事件处理-解压上传的压缩包
        // 数据内容校验、数据收集等
    }

    @Override
    public void handlerQueryList(ModelTaskListQueryEvent modelTaskListQueryEvent) {

    }

    @Override
    public void handlerQueryModelTaskInfo(YoloModelTaskInfoEvent yoloModelTaskInfoEvent) {
        // 事件处理-查询模型任务详情数据，添加模型相关数据，重赋值为子类对象
    }

    @Override
    public void handlerReportDataQuery(ReportDataQueryEvent reportDataQueryEvent) {
        //  事件处理-查询模型相关任务运行数据, 重赋值为子类对象
    }

    @Override
    public void handlerModelInfoQuery(ModelInfoQueryEvent modelInfoQueryEvent) {
        // 事件处理-查询模型相关详情数据
    }

    @Override
    public void handlerModelTaskResultDownloadUrlGet(ModelTaskResultDownloadUrlGetEvent modelTaskResultDownloadUrlGetEvent) {
        // 事件处理-查询模型训练结果文件
    }
}