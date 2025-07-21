package com.binninfo.model_train.utils;

import cn.hutool.core.text.StrPool;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class FileUtil {
    public static boolean  translateFile(File source, File dest){
        try (FileInputStream sourceFile = new FileInputStream(source);
             FileOutputStream destinationFile = new FileOutputStream(dest)){
            byte[] buffer = new byte[1024];
            int length;
            while ((length = sourceFile.read(buffer)) > 0) {
                destinationFile.write(buffer, 0, length);
            }
        }catch (IOException e){
            log.error("文件拷贝异常, 源文件:{} , 目标文件:{} ", source.getPath(), dest.getPath(), e);
            return false;
        }
        return true;
    }


    /**
     * 通用解压方法（根据文件后缀自动判断类型）
     */
    public static String decompress(File file, String destDir) throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        boolean isLinux = os.contains("nix") || os.contains("nux") || os.contains("aix");
        String filePath = file.getPath();
        if (filePath.endsWith(".zip")) {
            // ZIP通用解压
            return  unzip(filePath, destDir);
        } else if (filePath.endsWith(".rar")) {
            // RAR跨平台解压（需junrar）
            return unrar(filePath, destDir);
        } else if (filePath.endsWith(".gz")) {
            if (isLinux) {
                // 仅Linux解压GZ
                return unGzip(filePath, destDir);
            } else {
                throw new UnsupportedOperationException("非Linux系统不支持GZ解压");
            }
        } else {
            throw new IllegalArgumentException("不支持的压缩格式");
        }
    }

    /**
     * 解压ZIP文件（所有系统通用）
     */
    private static String unzip(String zipFilePath, String destDir) throws IOException {
        byte[] buffer = new byte[1024];
        String targetName = null;
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            if (zipEntry != null){
                targetName = zipEntry.getName();
            }
            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    newFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
        }
        return targetName;
    }

    /**
     * 解压RAR文件（跨平台，需junrar库）
     */
    public static String unrar(String rarFilePath, String destDir) throws RarException, IOException {
        File rarFile = new File(rarFilePath);
        File destDirFile = new File(destDir);
        String firstEntryName = null;

        try (Archive archive = new Archive(rarFile)) {
            FileHeader fh;
            while ((fh = archive.nextFileHeader()) != null) {
                // 标准化路径（兼容Windows/Unix）
                String entryName = fh.getFileNameString().replace('\\', File.separatorChar);
                // 记录第一个有效条目（跳过目录标记）
                if (firstEntryName == null) {
                    firstEntryName = new File(entryName).getName();
                }
                // 实际解压逻辑
                if (fh.isDirectory()) {
                    new File(destDirFile, entryName).mkdirs();
                    continue;
                }

                File outFile = new File(destDirFile, entryName);
                outFile.getParentFile().mkdirs();
                try (FileOutputStream os = new FileOutputStream(outFile)) {
                    archive.extractFile(fh, os);
                }
            }
        }
        return firstEntryName;
    }

    /**
     * 解压GZ文件（仅Linux）
     */
    public static String unGzip(String gzFilePath, String destDir) throws IOException {
        File gzFile = new File(gzFilePath);
        String outputFileName = gzFile.getName().replaceAll("\\.gz$", "");
        File outputFile = new File(destDir, outputFileName);
        try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(gzFile));
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
        return outputFileName;
    }

    public static void writeTo(String path, File file){
        boolean flag = FileUtil.translateFile(file, new File(path + StrPool.SLASH + file.getName()));
        if (Boolean.FALSE.equals(flag)){
            log.error("文件创建失败: ");
        }
    }

    public static File writeTo(String path, String fileName ,File file){
        File resultFile = new File(path + fileName);
        if (Boolean.FALSE.equals(FileUtil.translateFile(file, resultFile))){
            log.error("文件创建失败: ");
        }
        return resultFile;
    }


    public static boolean deleteFiles(File[] files){
        Boolean deleteFlag = Boolean.TRUE;
        for (File file : files) {
            if (file.isDirectory() && file.listFiles()!=null && file.listFiles().length>0){
                deleteFlag =  deleteFiles(file.listFiles());
            }
            if (Boolean.TRUE.equals(deleteFlag)){
                deleteFlag =  file.delete();
            }
        }
        return deleteFlag;
    }


    /**
     * 复制文件夹, 仅复制文件, 不复制子文件夹
     * @param or
     * @param target
     * @throws IOException
     */
    public static void copyFolder(File or, File target) throws IOException {
        if (or.isDirectory() && target.isDirectory()){
            File[] files = or.listFiles();
            for (File file : files) {
                if (!file.isDirectory()){
                    String fileName = file.getName();
                    translateFile(file,new File(target.getPath() + "/" +fileName));
                }
            }
        }
    }


    public static String calculateHashNio(File file, String algorithm)
            throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        try (FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.READ)) {
            long position = 0;
            long fileSize = channel.size();

            while (position < fileSize) {
                long remaining = fileSize - position;
                long chunkSize = Math.min(remaining, Integer.MAX_VALUE);

                MappedByteBuffer buffer = channel.map(
                        FileChannel.MapMode.READ_ONLY, position, chunkSize
                );
                digest.update(buffer);
                position += chunkSize;
            }
        }
        return bytesToHex(digest.digest());
    }

    // 字节数组转十六进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
