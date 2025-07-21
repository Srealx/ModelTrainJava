# 模型训练平台核心架构设计文档

------

## 系统架构概述

本系统采用分层架构设计，核心包含两大模块：

1. **系统操作层** - 抽象操作系统功能，实现跨平台兼容
2. **任务执行层** - 支持模型训练任务的步骤化执行和链式调用

系统通过能力接口(Capable Interfaces)解耦具体实现，通过注解驱动任务步骤注册，支持灵活扩展新模型训练流程。

## 核心设计理念

### 1. 跨平台系统抽象

- **操作系统接口**：`ISystemOperate` 提供统一入口
- **能力接口模式**：通过`*Capable`接口定义系统能力（解压、文件权限、进程执行等）
- **自动适配**：`SystemOperateRegistrar`根据当前OS自动选择实现类

### 2. 任务步骤化执行

- **链式任务**：将模型训练拆解为可组合的步骤
- **注解驱动**：`@ModelTaskStep`标注具体步骤实现
- **回调机制**：支持异步任务回调处理

### 3. 扩展性设计

- **能力接口**：新增系统功能只需实现对应`*Capable`接口
- **模板方法**：抽象基类提供通用逻辑，子类实现具体步骤
- **事件机制**：通过事件处理器扩展步骤行为

## 系统操作层设计

### 核心接口

|                接口                |       功能       |       实现类        |
| :--------------------------------: | :--------------: | :-----------------: |
|          `ISystemOperate`          | 操作系统基础接口 |   所有系统实现类    |
| `ICapableSystemFilePackageExtract` |  压缩包解压能力  | Windows/Linux实现类 |
|    `ISystemFileGivePermission`     |   文件权限管理   | Windows/Linux实现类 |
|      `ISystemProcessStarter`       |   进程执行能力   | Windows/Linux实现类 |

### 能力接口

![capacity](img\capacity.png)

### 实现机制

```java
// 获取当前系统适配的操作实例
ISystemFileGivePermission system = SystemOperateRegistrar.getOperate(ISystemFileGivePermission.class);

// 文件提权操作
system.give(trainingFile);
```

## 任务步骤执行架构

### 核心组件

![taskStep](img\taskStep.png)

### 执行流程

​    链式步骤执行器抽象设计

<img src="img\actuator.png" alt="actuator" style="zoom: 33%;" />

1. **步骤注册**：通过`@ModelTaskStep`注解声明步骤

   ```java
   @ModelTaskStep(model = ModelEnum.YOLO, stepNumber = 1)
   public class YoloGenerateYamlActuator extends ... { ... }
   ```

2. **参数构建**：`buildParam()`方法准备步骤参数

   ```java
   public YoloTaskStepExecuteDTO buildParam(TaskStepExecuteDTO dto) {
       // 转换参数类型
       return CommonUtil.copy(dto, YoloTaskStepExecuteDTO.class);
   }
   ```

3. **步骤执行**：`execute()`方法执行核心逻辑

   ```java
   public TaskStepResultDTO execute(YoloTaskStepExecuteDTO dto) {
       // 发送训练任务到Python端
       return executeRequest(result, modelTask.getFileUuid());
   }
   ```

4. **回调处理**：`handlerCallBack()`处理异步结果

   ```java
   public void handlerCallBack(ModelTaskCallBackParam param) {
       // 更新训练结果数据
   }
   ```

### 回调机制

![callBack](img\callBack.png)

## 扩展新模型训练任务指南

### 步骤1：创建模型步骤基类

```java
public abstract class NewModelBaseStep 
    extends ModelTaskChainedTaskStepActuatorStep<TaskStepResultDTO, NewModelStepDTO> {
    
    @Override
    public String getModelSubFolder() {
        return ModelEnum.NEW_MODEL.getModelFolder();
    }

    // 可选的公共事件处理
    private void handleCommonEvent(...) { ... }
}
```

### 步骤2：实现具体训练步骤

```java
@ModelTaskStep(model = ModelEnum.NEW_MODEL, stepNumber = 1)
public class NewModelDataPrepStep extends NewModelBaseStep {
    
    @Override
    public NewModelStepDTO buildParam(TaskStepExecuteDTO dto) {
        // 准备数据预处理参数
    }
    
    @Override
    public TaskStepResultDTO execute(NewModelStepDTO dto) {
        // 执行数据预处理
    }
}
```

### 步骤3：实现回调步骤（可选）

```java
@ModelTaskStep(model = ModelEnum.NEW_MODEL, stepNumber = 2)
public class NewModelTrainingStep 
    extends NewModelBaseStep 
    implements ICallBackHandler<TrainingCallback> {
    
    @Override
    public TaskStepResultDTO execute(NewModelStepDTO dto) {
        // 发起训练任务
    }
    
    @Override
    public void handlerCallBack(TrainingCallback callback) {
        // 处理训练结果回调
    }
}
```

### 步骤4：注册新模型类型

在`ModelEnum`中添加新模型：

```java
public enum ModelEnum {
    NEW_MODEL("newModel", "new_model_directory"),
    ...;
}
```

## 核心接口与类说明

### 系统操作核心接口

|              接口              |     功能说明     |          关键方法           |
| :----------------------------: | :--------------: | :-------------------------: |
|    `IPackageExtractCapable`    |  压缩包解压能力  |       `extract(File)`       |
|  `IFileGivePermissionCapable`  |   文件提权能力   |        `give(File)`         |
| `IPythonProcessStarterCapable` |  Python执行能力  | `invoke(String, String...)` |
|        `ISystemOperate`        | 系统操作基础接口 |      `getSystemName()`      |

### 任务执行核心类

|                   类                    |     功能说明     |      继承关系      |
| :-------------------------------------: | :--------------: | :----------------: |
|     `YoloModelTaskStepStepActuator`     |   YOLO任务基类   | 处理公共逻辑和事件 |
| `YoloCallBackModelTaskStepActuatorStep` | 带回调的步骤基类 |  实现回调处理框架  |
| `ModelTaskChainedTaskStepActuatorStep`  | 链式步骤执行基类 |  提供步骤连接能力  |