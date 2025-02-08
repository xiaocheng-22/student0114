package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
/*
@Configuration：标记配置类，Spring 会处理其中的 @Bean 方法。
@Bean：标记方法，返回值作为 Bean 注册到 Spring 容器。
通过这两个注解，开发者可以灵活配置 Spring 容器中的 Bean。
*/
@Configuration
public class RedisConfig {
    /*提供一个连接工厂，来与 Redis 数据库建立和管理连接*/
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /*设置键和值的序列化方式为 `StringRedisSerializer`，这意味着所有的键和值都将在 Redis 中以字符串形式存储*/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        //将值序列化为json格式
        //redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
