
package com.tuling.pqb;

import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot方式启动类
 *
 * @author gaohan
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication(exclude = {WebAutoConfiguration.class})
@EnableScheduling
public class GunsStartApplication {

    private final static Logger logger = LoggerFactory.getLogger(GunsStartApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GunsStartApplication.class, args);
        logger.info(GunsStartApplication.class.getSimpleName() + " is success!");
    }
}
