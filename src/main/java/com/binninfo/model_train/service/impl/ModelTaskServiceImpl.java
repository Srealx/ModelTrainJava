package com.binninfo.model_train.service.impl;

import com.binninfo.model_train.config.DelayProperties;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskStatusEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.delay.DelayQueueProvider;
import com.binninfo.model_train.service.delay.IDelayHandler;
import com.binninfo.model_train.service.event.task.BeforeCallbackReturnStepEvent;
import com.binninfo.model_train.service.event.task.BeforeHandlerCallbackStartStepEvent;
import com.binninfo.model_train.service.factory.ModelTaskStepActuatorFactory;
import com.binninfo.model_train.service.factory.ModelTaskServiceFactory;
import com.binninfo.model_train.service.stepactuator.ICallBackHandler;
import com.binninfo.model_train.service.stepactuator.IChainedTaskStepActuator;
import com.binninfo.model_train.service.stepactuator.IProgressType;
import com.binninfo.model_train.service.stepactuator.ModelTaskChainedTaskStepActuatorStep;
import com.binninfo.model_train.vo.dto.event.ModelTaskStepEventArgsDTO;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import com.binninfo.model_train.vo.param.ModelTaskCallBackParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 模型任务服务
 * @Author chengang
 * @Date 2025/6/17
 */
@Service
@Slf4j
public class ModelTaskServiceImpl implements IDelayHandler {
    ExecutorService threadPool = new ThreadPoolExecutor(
            3,
            5,
            60,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Resource
    ApplicationEventPublisher applicationEventPublisher;
    @Resource
    ModelTaskServiceFactory modelTaskServiceFactory;
    @Resource
    ModelTaskStepActuatorFactory modelTaskStepActuatorFactory;
    @Resource
    DelayQueueProvider delayQueueProvider;
    @Resource
    DelayProperties delayProperties;


    /**
     * 模型训练-执行任务
     */
    @Transactional(rollbackFor = Exception.class)
    public void task() {
        // 拿到待执行任务
        // 获取任务类型的对应实现
        IModelTrainService service = modelTaskServiceFactory.getService(ModelTaskTypeEnum.find(modelTask.getTestFlag()));
        // 任务开始
        try {
            TaskStepResultDTO taskStepResultDTO = service.runTask(modelTask);
            // 拿到训练结果, 更新数据
            if (Boolean.TRUE.equals(taskStepResultDTO.getSuccess())){
                // 更新任务状态
                // 加入延时队列标记防超时
                delayQueueProvider.addDelayedTask(this.getSign(), modelTask.getFileUuid(), delayProperties.getModelTaskSec(),TimeUnit.MINUTES);
            }else {
                // 错误处理
            }
        }catch (Exception e){
            // 报错处理
        }
    }

    /**
     * 模型任务回调处理
     * @param param param
     */
    @Transactional(rollbackFor = Exception.class)
    public void callBack(ModelTaskCallBackParam param){
        log.info("==========>>>>>> 接收到来自{}-{}的回调", param.getUuid(), param.getProgressCode());
        // 任务是否已结束
        if (Boolean.FALSE.equals(ModelTaskStatusEnum.find(modelTask.getTaskStatus()).equals(ModelTaskStatusEnum.START))){
            log.info("该任务已结束...  退出处理");
            return;
        }
        if (Boolean.FALSE.equals(param.getSuccess())){
            //  处理失败,更新任务状态为失败, 并退出任务
            modelTask.setTaskStatus(ModelTaskStatusEnum.FAIL.getCode());
            return;
        }
        // 事件发布, 在http请求结束前对请求资源进行操作，具体的操作者为对应的任务节点
        ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, TaskStepExecuteDTO> node = modelTaskStepActuatorFactory.getNode(ModelEnum.findByName(modelTask.getModel()),
                ModelTaskTypeEnum.find(modelTask.getTestFlag()),
                ModelEnum.findByName(modelTask.getModel()).getStepDesc(param.getProgressCode()));
        applicationEventPublisher.publishEvent(new BeforeCallbackReturnStepEvent(this,new ModelTaskStepEventArgsDTO(node,param)));
        // 异步执行任务后处理
        threadPool.execute(()->handlerCallback(param, modelTask,node));
    }

    @Transactional(rollbackFor = Exception.class)
    public void handlerCallback(ModelTaskCallBackParam param, ModelTask modelTask, ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, TaskStepExecuteDTO> node){
        // 开始进行节点回调后处理, 事件发布
        applicationEventPublisher.publishEvent(new BeforeHandlerCallbackStartStepEvent(this,new ModelTaskStepEventArgsDTO(node,param)));
        // 查询步骤名称
        IProgressType stepType = ModelEnum.findByName(modelTask.getModel()).getStepDesc(param.getProgressCode());
        String stepDesc = stepType.getStepName();
        // 执行步骤后处理
        if (node instanceof ICallBackHandler){
            @SuppressWarnings("unchecked")
            ICallBackHandler<Object> handler = (ICallBackHandler<Object>) node;
            try {
                handler.handlerCallBack(param);
            }catch (Exception e){
                //  任务失败, 更新状态
                modelTask.setTaskStatus(ModelTaskStatusEnum.FAIL.getCode());
                return;
            }
        }
        // 进行下一步处理
        IChainedTaskStepActuator<TaskStepResultDTO, TaskStepExecuteDTO> nextNode = node.getNextNode();
        boolean endNode = modelTaskStepActuatorFactory.isEndNode(ModelEnum.findByName(modelTask.getTaskName()), ModelTaskTypeEnum.find(modelTask.getTestFlag()), node.getStep());
        if (Boolean.TRUE.equals(endNode)) {
            // 已完成所有步骤, 任务完成, 更新状态
            modelTask.setTaskStatus(ModelTaskStatusEnum.SUCCEED.getCode());
            return;
        }
        String nextStepDesc = ModelEnum.findByName(modelTask.getModel()).getStepDesc(nextNode.getStep().getTypeCode()).getStepName();
        log.info("任务 {} 进行到下一步骤: {}", modelTask.getFileUuid(),nextNode.getStep().getTypeCode());
        TaskStepExecuteDTO taskStepExecuteDTO = new TaskStepExecuteDTO()
                .setModelTask(modelTask)
                .setModelTaskTypeEnum(ModelTaskTypeEnum.find(modelTask.getTestFlag()));
        TaskStepResultDTO stepResultDTO;
        try {
            stepResultDTO = nextNode.execute(nextNode.buildParam(taskStepExecuteDTO));
        }catch (Exception e){
            //  任务失败, 更新状态
            modelTask.setTaskStatus(ModelTaskStatusEnum.FAIL.getCode());
            return;
        }
        // 如果失败更新
        if (Boolean.FALSE.equals(stepResultDTO.getSuccess())){
            log.info("任务 {} 执行步骤 {} 失败, 失败原因: {}", modelTask.getFileUuid(),nextStepDesc,stepResultDTO.getMessage());
            // 更新任务状态
            modelTask.setTaskStatus(ModelTaskStatusEnum.FAIL.getCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlerDelay(String uuid) {
        if (modelTask.getTaskStatus().equals(ModelTaskStatusEnum.START.getCode())){
            // 更新状态为失败
            modelTask.setTaskStatus(ModelTaskStatusEnum.FAIL.getCode());
            modelTaskMapper.updateById(modelTask);
        }
    }

    @Override
    public String getSign() {
        return "model_task";
    }
}
