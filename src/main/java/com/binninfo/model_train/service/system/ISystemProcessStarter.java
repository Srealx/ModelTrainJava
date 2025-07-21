package com.binninfo.model_train.service.system;

import com.binninfo.model_train.service.system.capacity.IPythonProcessStarterCapable;
import org.assertj.core.util.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 系统-进程执行
 * @Author chengang
 * @Date 2025/6/17
 */
public interface ISystemProcessStarter extends ISystemOperate, IPythonProcessStarterCapable {

    default <R> R invoke(Function<PythonExecutionResult,R> resultHandler,String pyPath,  String... args){
        return resultHandler.apply(invoke(pyPath, args));
    }

    @Override
    default PythonExecutionResult invoke(String pyPath, String... args){
        PythonExecutionResult result = new PythonExecutionResult();
        Process process = null;

        try {
            // 构建命令
            List<String> command = Lists.newArrayList();
            // 使用系统默认的Python解释器
            command.add("python");
            command.add(pyPath);
            command.addAll(Arrays.asList(args));

            // 创建进程
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // 设置环境变量 - 强制使用UTF-8编码
            Map<String, String> env = processBuilder.environment();
            env.put("PYTHONIOENCODING", "utf-8");
            env.put("PYTHONUTF8", "1");
            env.put("LC_ALL", "en_US.UTF-8");

            // 合并标准输出和错误输出
            processBuilder.redirectErrorStream(true);

            // 启动进程
            process = processBuilder.start();

            // 读取输出流
            StringBuilder output = new StringBuilder();
            try (InputStream inputStream = process.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    output.append(line).append("\n");
                }
            }

            // 等待进程结束（设置超时时间）
            boolean exited = process.waitFor(30, TimeUnit.MINUTES);
            if (!exited) {
                // 超时处理
                process.destroyForcibly();
                result.setExitCode(-1);
                result.setErrorOutput("Python脚本执行超时（30分钟）");
                return result;
            }

            // 获取退出码
            int exitCode = process.exitValue();
            result.setExitCode(exitCode);

            if (exitCode == 0 && Boolean.FALSE.equals(output.toString().contains("错误"))) {
                // 执行成功
                result.setOutput(output.toString());
            } else {
                // 执行失败
                result.setErrorOutput(output.toString());
                if (Boolean.TRUE.equals(output.toString().contains("错误"))){
                    result.setExitCode(-1);
                }
            }

        } catch (IOException | InterruptedException e) {
            // 处理Java端的异常
            result.setException(e);
            result.setExitCode(-1);
            result.setErrorOutput("Java执行异常: " + e.getMessage());

            // 确保进程被终止
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }

            // 恢复中断状态
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }

        return result;
    }
}
