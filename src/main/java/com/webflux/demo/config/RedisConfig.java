package com.webflux.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, Object>
    reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory){

        RedisSerializationContext<String, Object> context = RedisSerializationContext
                .<String, Object> newSerializationContext(RedisSerializer.string()) // key
                .value(RedisSerializer.json()) // value json
                .hashKey(RedisSerializer.string())
                .hashValue(RedisSerializer.string())
                .build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }
}
