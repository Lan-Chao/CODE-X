package com.silent.demo;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisConditionDemo  implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        try {
            Class<?>  aClass = Class.forName("org.springframework.data.redis.core.RedisTemplate");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
