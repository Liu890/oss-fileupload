package com.boss.soft.oss.thread;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;

import java.io.File;

/**
 * @author ljx
 * @date 2020/7/23 15:06
 */
public class FileDownloadThread implements Runnable {
    private OSS ossClient;
    private String bucketName;
    private String fileName;

    public FileDownloadThread(OSS ossClient, String bucketName, String fileName) {
        this.ossClient = ossClient;
        this.bucketName = bucketName;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
        File file = new File(fileName);
        ossClient.getObject(getObjectRequest,file);
        ossClient.shutdown();
    }
}
