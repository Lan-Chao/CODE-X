package com.silent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author zhaochangren
 * @Title: CODE
 * @ProjectName CODE-X
 * @Description:
 * @date 2020/5/28 11:45
 */

@EnableCaching
@SpringBootApplication
public class CODE  implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(CODE.class, args);


        System.out.println("hello");


    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("ddssgdf");
    }
}
