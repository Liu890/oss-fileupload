package com.boss.soft.oss.thread;

import com.aliyun.oss.OSS;
import com.boss.soft.oss.config.AliyunConfig;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.*;

/**
 * @author ljx
 * @date 2020/7/24 11:08
 */
public class FileUploadText {

    @Autowired
    private static OSS ossClient;
    @Autowired
    private static AliyunConfig aliyunConfig;
    private static Object FileUploadThread;

    public static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args)  {
        String fileName1="http.jpeg";
        String filePath1="D:\\picture\\http.jpeg";
        String fileName2="http.jpeg";
        String filePath2="D:\\picture\\http.jpeg";
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("oss-text-pool-%d").build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024),
                threadFactory, new ThreadPoolExecutor.AbortPolicy());
        new Thread(()->{
            FileUploadThread fileUploadThread1 = new FileUploadThread(ossClient,aliyunConfig,fileName1,filePath1);
        }).start();
        new Thread(()->{
            FileUploadThread fileUploadThread2 = new FileUploadThread(ossClient,aliyunConfig,fileName2,filePath2);
        }).start();
        executor.shutdown();
    }

}
