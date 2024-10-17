package com.api.Weather.component;

import com.api.Weather.model.WeatherModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;
import java.util.Objects;

public class WeatherSerializer implements RedisSerializer<WeatherModel> {

    private final ObjectMapper objectMapper;

    public WeatherSerializer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(WeatherModel value) throws SerializationException {
        try{
            return objectMapper.writeValueAsBytes(value);
        }
        catch (JsonProcessingException e) {
            throw new SerializationException("Error Serializing Weather to JSON", e);
        }
    }

    @Override
    public WeatherModel deserialize(byte[] bytes) throws SerializationException {
        try {
            if(Objects.nonNull(bytes)){
                return objectMapper.readValue(bytes, WeatherModel.class);
            }
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new SerializationException("Error Deserializing JSON To Weather", e);
        }

    }
}
