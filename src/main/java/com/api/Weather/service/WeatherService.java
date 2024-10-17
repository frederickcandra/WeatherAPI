package com.api.Weather.service;

import com.api.Weather.model.WeatherModel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    private static final String API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/tangerang?unitGroup=metric&key=968QQR7C5UCK9ZTNAT294PUCX&contentType=json";

    private final RedisTemplate<String, WeatherModel> redisTemplate;
    private final RedisService redisService;

    public WeatherService(RedisTemplate<String, WeatherModel> redisTemplate, RedisService redisService) {
        this.redisTemplate = redisTemplate;
        this.redisService = redisService;
    }

    public WeatherModel GetWeather() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);

        if (response != null && response.containsKey("days")) {
            List<Map<String, Object>> days = (List<Map<String, Object>>) response.get("days");

            // Siapkan daftar untuk menampung model cuaca
            List<WeatherModel> weatherModels = new ArrayList<>();

            for (Map<String, Object> dayWeather : days) {
                // Ambil data cuaca dari setiap hari
                String description = (String) dayWeather.get("description");
                String humidity = String.valueOf(dayWeather.get("humidity"));
                String temp = String.valueOf(dayWeather.get("temp"));
                String cityName = (String) response.get("resolvedAddress");
                String dateTime = (String) dayWeather.get("datetime");

                // Buat model cuaca untuk setiap hari
                WeatherModel weatherModel = new WeatherModel(cityName, description, humidity, temp, dateTime);

                // Tambahkan model ke daftar
                weatherModels.add(weatherModel);
            }

            // Simpan data ke Redis melalui RedisService
            redisService.saveWeather("Weather", weatherModels.get(0));

            return weatherModels.get(0); // Mengembalikan daftar model cuaca
        }
        return null; // Jika data tidak ada
    }


    public WeatherModel getWeatherFromRedis() {
        return redisTemplate.opsForValue().get("Weather");
    }
}
