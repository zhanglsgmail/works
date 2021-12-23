package com.asiainfo.ai.framework.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@Configuration
public class RedisConfig {
//    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //非final类型的对象，把对象类型也序列化进去，以便反序列化推测正确的类型
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //null字段不显示
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //POJO无public属性或方法时不报错
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}