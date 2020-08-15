package com.xiesx.fastboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;

/**
 * @title GotvApplication
 * @description SpringBoot启动入口
 * @author XIE
 * @date 2020年1月6日下午2:06:58
 */
@Slf4j
@SpringBootApplication(exclude = ValidationAutoConfiguration.class)
public class FastBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FastBootApplication.class);
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.run(args);
        log.info("Started FastBootApplication 启动成功");
    }
}
