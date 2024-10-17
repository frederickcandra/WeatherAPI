package com.api.Weather.config;

import com.api.Weather.component.WeatherSerializer;
import com.api.Weather.model.WeatherModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, WeatherModel> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, WeatherModel> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new WeatherSerializer(new ObjectMapper()));

        return redisTemplate;
    }
}
