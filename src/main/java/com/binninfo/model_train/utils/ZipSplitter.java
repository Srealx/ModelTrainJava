package com.binninfo.model_train.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ZipSplitter {

    // 分割ZIP文件
    public static void splitZip(String zipPath, long splitSizeBytes, String outputDir) throws IOException {
        File inputFile = new File(zipPath);
        if (!inputFile.exists()) {
            throw new FileNotFoundException("ZIP file not found: " + zipPath);
        }

        // 确保输出目录存在
        Files.createDirectories(Paths.get(outputDir));

        try (FileInputStream fis = new FileInputStream(inputFile);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            byte[] buffer = new byte[1024 * 1024]; // 1MB缓冲区
            int bytesRead;
            long totalBytesRead = 0;
            int partCounter = 1;

            // 计算分片文件名模板
            String baseName = inputFile.getName() + ".part";
            File currentPartFile = new File(outputDir, baseName + String.format("%03d", partCounter));

            try (FileOutputStream fos = new FileOutputStream(currentPartFile)) {
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                while ((bytesRead = bis.read(buffer)) != -1) {
                    // 检查是否需要切换到新分片
                    if (totalBytesRead + bytesRead > splitSizeBytes * partCounter) {
                        bos.close(); // 关闭当前分片
                        
                        // 创建新分片
                        partCounter++;
                        currentPartFile = new File(outputDir, baseName + String.format("%03d", partCounter));
                        bos = new BufferedOutputStream(new FileOutputStream(currentPartFile));
                    }
                    
                    bos.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                }
            }
            System.out.println("Split complete! Total parts: " + partCounter);
        }
    }

    // 合并分片文件
    public static void mergeParts(String inputDir, String outputZipPath) throws IOException {
        File dir = new File(inputDir);
        File[] parts = dir.listFiles((d, name) -> name.endsWith(".part"));
        if (parts == null || parts.length == 0) {
            throw new FileNotFoundException("No part files found in " + inputDir);
        }

        // 按文件名排序确保正确顺序
        java.util.Arrays.sort(parts);

        try (FileOutputStream fos = new FileOutputStream(outputZipPath);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            for (File part : parts) {
                Files.copy(part.toPath(), bos);
            }
        }
        System.out.println("Merge complete! Output: " + outputZipPath);
    }

    public static void main(String[] args) {
        try {
            // 示例用法
            String zipPath = "C:\\Users\\Srealx\\OneDrive\\Desktop\\modelTrain\\充电枪没复位bak.zip";
            String outputDir = "C:\\Users\\Srealx\\OneDrive\\Desktop\\modelTrain\\split";
            long splitSize = 50 * 1024 * 1024; // 50MB
            
            // 分割文件
            splitZip(zipPath, splitSize, outputDir);
            
            // 合并文件
//            mergeParts(outputDir, "restored.zip");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}