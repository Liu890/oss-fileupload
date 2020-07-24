package com.boss.soft.oss.thread;

import com.aliyun.oss.OSS;
import com.boss.soft.oss.config.AliyunConfig;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import static com.boss.soft.oss.thread.FileUploadText.THREAD_POOL_SIZE;

/**
 * @author ljx
 * @date 2020/7/24 12:02
 */
public class FileDownloadText {
    @Autowired
    private static OSS ossClient;
    @Autowired
    private static AliyunConfig aliyunConfig;

    public static void main(String[] args) {
        String fileName1 = "images/2020/07/23/15954898689363284.jpg";
        String fileName2 = "images/2020/07/23/15954967083166353.jpg";
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("oss-text-pool-%d").build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024),
                threadFactory, new ThreadPoolExecutor.AbortPolicy());
        new Thread(()->{
            new FileDownloadThread(ossClient,aliyunConfig.getBucketName(),fileName1);
        }).start();


        new Thread(()->{
            new FileDownloadThread(ossClient,aliyunConfig.getBucketName(),fileName2);
        }).start();


    }


}
