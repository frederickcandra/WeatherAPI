package com.api.Weather.service;

import com.api.Weather.model.WeatherModel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, WeatherModel> redisTemplate;

    public RedisService(RedisTemplate<String, WeatherModel> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveWeather(String key, WeatherModel weather) {
            redisTemplate.opsForValue().set(key, weather);
    }

}
