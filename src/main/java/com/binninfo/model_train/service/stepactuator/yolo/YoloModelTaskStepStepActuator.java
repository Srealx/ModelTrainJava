package com.binninfo.model_train.service.stepactuator.yolo;

import com.binninfo.excelcommon.utils.CommonUtil;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.service.event.task.BeforeHandlerCallbackStartStepEvent;
import com.binninfo.model_train.service.stepactuator.ModelTaskChainedTaskStepActuatorStep;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import com.binninfo.model_train.vo.dto.YoloTaskStepExecuteDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * yolo任务步骤
 * @Author chengang
 * @Date 2025/6/30
 */
@Slf4j
public abstract class YoloModelTaskStepStepActuator extends ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, YoloTaskStepExecuteDTO> {

    public YoloModelTaskStepStepActuator(){
        // 添加事件处理器
        this.addModelTaskEventHandler(BeforeHandlerCallbackStartStepEvent.class, event -> handleBeforeHandlerCallbackStartEvent((BeforeHandlerCallbackStartStepEvent)event));
    }

    @Override
    public String getModelSubFolder(){
        return ModelEnum.YOLO.getModelFolder();
    }

    @Override
    public String model(){
        return ModelEnum.YOLO.getName();
    }

    @Override
    public YoloTaskStepExecuteDTO buildParam(TaskStepExecuteDTO taskStepExecuteDTO){
        return CommonUtil.copy(taskStepExecuteDTO,YoloTaskStepExecuteDTO.class);
    }

    /**
     * yolo模型任务对回调的处理, 由于发送yolo任务的python方整个处理流程都是单线程, 所以当处理任务触发后需要让线程睡眠一点时间，
     * 来使回调接口响应给python后python释放线程再往下走，否则再请求python可能回失败
     * @param event {@link BeforeHandlerCallbackStartStepEvent}
     */
    private void handleBeforeHandlerCallbackStartEvent(BeforeHandlerCallbackStartStepEvent event) {
        int sleepTime = 5;
        log.info("任务: "+ event.getModelTaskStepEventArgsDTO().getModelTaskCallBackParam().getUuid() + "进入休眠状态, 休眠时间: " + sleepTime + "秒");
        // 这里睡眠5秒, 来保证足够的响应时间
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            log.error("线程休眠失败...   ");
        }
    }
}
