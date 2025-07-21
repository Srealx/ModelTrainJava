package com.binninfo.model_train.service.system;

import lombok.extern.slf4j.Slf4j;

/**
 * linux系统操作
 * @Author chengang
 * @Date 2025/5/12
 */
@Slf4j
public class UbuntuLinuxSystemOperate extends LinuxSystemOperate{
    private static UbuntuLinuxSystemOperate ubuntuLinuxSystemOperate;
    protected UbuntuLinuxSystemOperate(){}
    public static UbuntuLinuxSystemOperate obtain(){
        if (ubuntuLinuxSystemOperate == null){
            ubuntuLinuxSystemOperate = new UbuntuLinuxSystemOperate();
        }
        return ubuntuLinuxSystemOperate;
    }

    @Override
    public String getSystemName() {
        return "bun";
    }

}
