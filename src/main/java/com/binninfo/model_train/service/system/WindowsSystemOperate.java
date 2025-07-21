package com.binninfo.model_train.service.system;

import com.binninfo.model_train.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * linux系统操作
 * @Author chengang
 * @Date 2025/5/12
 */
@Slf4j
public class WindowsSystemOperate implements ICapableSystemFilePackageExtract , ISystemFileGivePermission, ISystemProcessStarter {
    private static WindowsSystemOperate windowsSystemOperate;
    protected WindowsSystemOperate(){}

    public static WindowsSystemOperate obtain(){
        if (windowsSystemOperate == null){
            windowsSystemOperate = new WindowsSystemOperate();
        }
        return windowsSystemOperate;
    }

    @Override
    public String getSystemName() {
        return "indows";
    }

    /**
     * 文件解压能力map
     */
    private static Map<String, Function<File,String>> EXTRACT_FILE_PACKAGE_CAPACITY_MAP = new HashMap();
    static {
        EXTRACT_FILE_PACKAGE_CAPACITY_MAP.put("zip", WindowsSystemOperate::commonExtract);
        EXTRACT_FILE_PACKAGE_CAPACITY_MAP.put("rar", WindowsSystemOperate::commonExtract);
    }


    @Override
    public Map<String, Function<File,String>> getFilePackageExtractMap() {
        return EXTRACT_FILE_PACKAGE_CAPACITY_MAP;
    }

    private static String commonExtract(File file){
        String filePath = file.getPath();
        String fileName = file.getName();
        try {
            return FileUtil.decompress(file,filePath.replaceFirst(fileName,""));
        }catch (Exception e){
            log.error("文件解压缩失败: ",e);
            throw new CustomException("系统异常, 压缩包解压失败");
        }
    }

    @Override
    public boolean give(File file) {
        try {
            // 获取当前用户（也可替换为目标用户/组）
            UserPrincipal owner = Files.getOwner(file.toPath());
            // 创建 ACL 条目：赋予完全控制权
            AclEntry entry = AclEntry.newBuilder()
                    .setType(AclEntryType.ALLOW)
                    .setPrincipal(owner)
                    .setPermissions(
                            AclEntryPermission.READ_DATA,
                            AclEntryPermission.WRITE_DATA,
                            AclEntryPermission.APPEND_DATA,
                            AclEntryPermission.EXECUTE,
                            AclEntryPermission.DELETE,
                            AclEntryPermission.READ_ATTRIBUTES,
                            AclEntryPermission.WRITE_ATTRIBUTES
                    )
                    .build();
            // 应用 ACL
            AclFileAttributeView aclView = Files.getFileAttributeView(file.toPath(), AclFileAttributeView.class);
            List<AclEntry> acl = new ArrayList<>(aclView.getAcl());
            // 添加到权限列表顶部（优先级最高）
            acl.add(0, entry);
            aclView.setAcl(acl);
        }catch (Exception e){
            log.error("文件提权失败:  ",e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
