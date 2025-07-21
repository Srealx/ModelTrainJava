package com.binninfo.model_train.service.impl;

import com.binninfo.excelcommon.beans.page.PageDTO;
import com.binninfo.model_train.bean.ModelTask;
import com.binninfo.model_train.constant.enums.ModelEnum;
import com.binninfo.model_train.constant.enums.ModelTaskTypeEnum;
import com.binninfo.model_train.service.stepactuator.IChainedTaskStepActuator;
import com.binninfo.model_train.vo.ModelTaskListVO;
import com.binninfo.model_train.vo.ModelTaskInfoVO;
import com.binninfo.model_train.vo.ModelTaskReportVO;
import com.binninfo.model_train.vo.ModelTaskResultFileDownloadVO;
import com.binninfo.model_train.vo.dto.TaskStepExecuteDTO;
import com.binninfo.model_train.vo.dto.TaskStepResultDTO;
import com.binninfo.model_train.vo.param.ModelTrainListQueryParam;
import com.binninfo.model_train.vo.param.ModelTaskAddParam;

/**
 * 模型训练任务接口
 * @Author chengang
 * @Date 2025/5/12
 */
public interface IModelTrainService {
    /**
     * 任务类型
     * @return {@link ModelTaskTypeEnum}
     */
    ModelTaskTypeEnum getTaskType();

    /**
     * 添加训练任务
     * @param modelTaskAddParam {@link ModelTaskAddParam}
     */
    void addTask(ModelTaskAddParam modelTaskAddParam);

    /**
     * 查询列表
     * @param param {@link ModelTrainListQueryParam}
     * @return {@link PageDTO< ModelTaskListVO >}
     */
    PageDTO<ModelTaskListVO> queryList(ModelTrainListQueryParam param);

    /**
     * 根据id查询任务
     * @param id id
     * @return {@link ModelTask}
     */
    ModelTask getModelTrainTaskById(Integer id);

    /**
     * 查询任务详情
     * @return {@link ModelTaskInfoVO}
     */
    ModelTaskInfoVO info(Integer id);

    /**
     * 查询任务运行结果报表图
     * @param taskId 任务id
     * @return {@link ModelTaskReportVO}
     */
    ModelTaskReportVO queryReport(Integer taskId);

    /**
     * 执行训练任务
     * @param modelTask {@link ModelTask}
     */
    TaskStepResultDTO runTask(ModelTask modelTask);

    /**
     * 获取任务开始节点
     * @return {@link IChainedTaskStepActuator}
     */
    IChainedTaskStepActuator<TaskStepResultDTO, TaskStepExecuteDTO> getStepActuator(ModelEnum modelEnum);

    /**
     * 获取模型任务结果的模型下载路径
     * @param id {@link Integer}
     * @return {@link String}
     */
    ModelTaskResultFileDownloadVO getTaskResultModelDownloadUrl(Integer id);
}
