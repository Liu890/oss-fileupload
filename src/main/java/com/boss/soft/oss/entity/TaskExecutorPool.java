package com.boss.soft.oss.entity;

import com.boss.soft.oss.config.ThreadConfig;
import com.boss.soft.oss.service.impl.ThreadFileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ljx
 * @date 2020/7/27 12:02
 */
@Configuration
@EnableAsync
public class TaskExecutorPool {

    @Autowired
    private ThreadConfig config;
    private static final Logger logger = LoggerFactory.getLogger(ThreadFileServiceImpl.class);

    @Bean
    public Executor taskExecutorPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        executor.setThreadNamePrefix("OssExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        logger.info(executor.getThreadNamePrefix());
        executor.initialize();
        return executor;
    }
}
