package com.boss.soft.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ljx
 * @date 2020/7/27 11:37
 */
@Data
@ConfigurationProperties(prefix = "task.pool")
public class ThreadConfig {

    private int corePoolSize;

    private int maxPoolSize;

    private int keepAliveSeconds;

    private int queueCapacity;

}
