package com.silent.configuration;


import com.silent.demo.RedisConditionDemo;
import com.silent.model.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    /**
     *   eg:  @ConditionalOnClass(name = "com.silent.controller.TestController")
     *
     * */
    @Bean
    @Conditional(RedisConditionDemo.class)
    public Person person(){
        return new Person();
    }

}
