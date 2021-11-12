package com.silent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhao
 * @date 2021/11/12 10:30
 */

@Slf4j
@EnableCaching
@EnableAsync
@SpringBootApplication
@SuppressWarnings("")
public class CODE {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(CODE.class, args);
        System.out.println("hello");

    }
}
