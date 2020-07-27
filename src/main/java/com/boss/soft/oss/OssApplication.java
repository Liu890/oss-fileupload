package com.boss.soft.oss;

import com.boss.soft.oss.config.ThreadConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author ljx
 * @date 2020/7/23 10:34
 */
@SpringBootApplication
@EnableConfigurationProperties({ThreadConfig.class})
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }

}
