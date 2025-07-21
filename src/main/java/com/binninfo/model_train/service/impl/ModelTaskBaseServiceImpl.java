package com.binninfo.model_train.service.impl;

import com.binninfo.excelcommon.beans.page.PageDTO;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.service.event.bus.ModelTaskAddEvent;
import com.binninfo.model_train.service.event.bus.ModelTaskResultDownloadUrlGetEvent;
import com.binninfo.model_train.service.processor.IListQueryPostBusinessProcessor;
import com.binninfo.model_train.service.processor.IQueryInfoPostBusinessProcessor;
import com.binninfo.model_train.service.stepactuator.IChainedTaskStepActuator;
import com.binninfo.model_train.vo.*;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import com.binninfo.model_train.vo.dto.event.ModelTaskAddEventDTO;
import com.binninfo.model_train.vo.param.ModelTrainListQueryParam;
import com.binninfo.model_train.vo.param.ModelTaskAddParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 模型训练-基础 服务
 * @author chen gang
 * @date 2025/5/7
 */
@Slf4j
public abstract class ModelTaskBaseServiceImpl
                                   implements IModelTrainService , IListQueryPostBusinessProcessor<ModelTaskListVO>,
                                              IAddEntityPostBusinessProcessor<ModelTask> , IQueryInfoPostBusinessProcessor<ModelTaskInfoVO> {
    @Resource
    ApplicationEventPublisher applicationEventPublisher;

    /**
     * 新增训练任务-基础
     * @param modelTaskAddParam {@link ModelTaskAddParam}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTask(ModelTaskAddParam modelTaskAddParam) {
        // 参数校验, 文件解压处理等
        // 实例化数据
        // 后置处理
        this.addEntityProcessor(modelTaskAddParam, modelTask);
    }
    protected void afterAddPublishEvent(ModelTaskAddParam modelTrainTaskAddParam, ModelTask entity){
        // 发送事件-新增task事件
        ModelTaskAddEventDTO modelTaskAddEventDTO = new ModelTaskAddEventDTO();
        modelTaskAddEventDTO.setModelTaskAddParam(modelTrainTaskAddParam);
        modelTaskAddEventDTO.setEntity(entity);
        modelTaskAddEventDTO.setModelEnum(ModelEnum.findByName(entity.getModel()));
        modelTaskAddEventDTO.setModelTaskTypeEnum(this.getTaskType());
        ModelTaskAddEvent modelTaskAddEvent = new ModelTaskAddEvent(this,modelTaskAddEventDTO);
        applicationEventPublisher.publishEvent(modelTaskAddEvent);
    }

    @Override
    public PageDTO<ModelTaskListVO> queryList(ModelTrainListQueryParam param){
        // 数据查询、组装
        // 执行后置操作
        returnList = this.listPostProcessor(returnList, ModelEnum.findByName(param.getModel()));
        // 组装返回对象
        return PageDTO.getPage(returnList);
    }

    @Override
    public ModelTaskInfoVO info(Integer id){
        // 查询基础任务信息
        // 执行后置处理
        return this.queryInfoProcessor(modelTaskInfoVO);
    }

    /**
     * 查询任务运行结果报表图
     * @param taskId 任务id
     * @return {@link ModelTaskReportVO}
     */
    @Override
    public ModelTaskReportVO queryReport(Integer taskId){
        // 查询基础数据: 原始上传数据
        // 事件发布:  交由具体模型事件处理器查询专属数据
        applicationEventPublisher.publishEvent(reportDataQueryEvent);
        return reportDataQueryEvent.getModelBusEventArgsDTO();
    }

    @Override
    public ModelTaskResultFileDownloadVO getTaskResultModelDownloadUrl(Integer id){
        //  查询任务信息
        ModelTaskResultDownloadUrlGetEvent modelTaskResultDownloadUrlGetEvent = new ModelTaskResultDownloadUrlGetEvent(this, modelTaskResultDownloadUrlGetDTO);
        // 发布事件，将模型的获取动作交给模型事件处理器实现
        applicationEventPublisher.publishEvent(modelTaskResultDownloadUrlGetEvent);
        return modelTaskResultDownloadUrlGetEvent.getModelBusEventArgsDTO().getModelTaskResultFileDownloadVO();
    }

    /**
     * 执行任务服务，首先通过 this.getStepActuator() 方法获取任务类型配置的对应的执行链的第一个节点，然后构建任务流程所需的参数 通过doRunTask执行
     */
    @Override
    public TaskStepResultDTO runTask(ModelTask modelTask) {
        IChainedTaskStepActuator<TaskStepResultDTO, TaskStepExecuteDTO> startNode = this.getStepActuator(ModelEnum.findByName(modelTask.getModel()));
        TaskStepExecuteDTO taskStepExecuteDTO = new TaskStepExecuteDTO()
                .setModelTask(modelTask)
                .setModelTaskTypeEnum(this.getTaskType());
        return startNode.execute(startNode.buildParam(taskStepExecuteDTO));
    }

}
