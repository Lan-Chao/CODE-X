package com.silent;

import com.silent.model.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

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

        ConfigurableApplicationContext context =
                SpringApplication.run(CODE.class, args);

        Object redis = context.getBean("person");
        System.out.println(redis);


        System.out.println("hello");


    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("ddssgdf");
    }
}
