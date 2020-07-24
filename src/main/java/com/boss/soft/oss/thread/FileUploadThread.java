package com.boss.soft.oss.thread;

import com.aliyun.oss.OSS;
import com.boss.soft.oss.config.AliyunConfig;
import com.boss.soft.oss.service.impl.FileUploadServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author ljx
 * @date 2020/7/24 10:54
 */
public class FileUploadThread implements Runnable {

    private OSS ossClient;
    private AliyunConfig aliyunConfig;
    private String filePath;
    private String fileName;

    public FileUploadThread(OSS ossClient, AliyunConfig aliyunConfig, String fileName,String filePath) {
        this.ossClient = ossClient;
        this.aliyunConfig = aliyunConfig;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        ossClient.putObject(aliyunConfig.getBucketName(), fileName, new File(filePath));
    }
}
