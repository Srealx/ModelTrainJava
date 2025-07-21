package com.binninfo.model_train.service.system;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2025/5/12
 */
@Slf4j
public class SystemOperateRegistrar {
    public static List<ISystemOperate> systemOperateList;
    static {
        systemOperateList = new ArrayList<>();
        systemOperateList.add(LinuxSystemOperate.obtain());
        systemOperateList.add(WindowsSystemOperate.obtain());
        systemOperateList.add(UbuntuLinuxSystemOperate.obtain());
    }

    public static <T extends ISystemOperate> T getOperate(Class<T> tClass){
        String osName = System.getProperty("os.name");
        for (ISystemOperate item : systemOperateList) {
            if (osName.contains(item.getSystemName()) && tClass.isAssignableFrom(item.getClass())){
                return (T)item;
            }
        }
        return null;
    }

}
