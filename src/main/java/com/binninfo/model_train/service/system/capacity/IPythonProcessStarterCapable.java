package com.binninfo.model_train.service.system.capacity;

/**
 * 系统能力-执行python脚本
 * @Author chengang
 * @Date 2025/6/17
 */
public interface IPythonProcessStarterCapable extends IProcessStarterCapable{
    /**
     * 执行python脚本
     * @param pyPath py文件位置
     * @param args 参数
     * @return {@link PythonExecutionResult}
     */
    PythonExecutionResult invoke(String pyPath, String... args);
}
