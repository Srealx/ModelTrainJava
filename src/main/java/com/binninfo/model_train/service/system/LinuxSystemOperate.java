package com.binninfo.model_train.service.system;

import com.binninfo.model_train.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * linux系统操作
 * @Author chengang
 * @Date 2025/5/12
 */
@Slf4j
public class LinuxSystemOperate implements ICapableSystemFilePackageExtract,ISystemFileGivePermission, ISystemProcessStarter {
    private static LinuxSystemOperate linuxSystemOperate;
    protected LinuxSystemOperate(){}
    public static LinuxSystemOperate obtain(){
        if (linuxSystemOperate == null){
            linuxSystemOperate = new LinuxSystemOperate();
        }
        return linuxSystemOperate;
    }

    @Override
    public String getSystemName() {
        return "inux";
    }

    /**
     * 文件解压能力map
     */
    private static Map<String, Function<File,String>> EXTRACT_FILE_PACKAGE_CAPACITY_MAP = new HashMap();
    static {
        EXTRACT_FILE_PACKAGE_CAPACITY_MAP.put("zip", LinuxSystemOperate::commonExtract);
        EXTRACT_FILE_PACKAGE_CAPACITY_MAP.put("rar", LinuxSystemOperate::commonExtract);
        EXTRACT_FILE_PACKAGE_CAPACITY_MAP.put("gz", LinuxSystemOperate::commonExtract);
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
        Set<PosixFilePermission> perms = new HashSet<>();
        // 添加所有权限：用户/组/其他均可读写执行 (rwxrwxrwx)
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        try {
            Files.setPosixFilePermissions(file.toPath(), perms);
        } catch (IOException e) {
            log.error("文件提权失败: ",e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
