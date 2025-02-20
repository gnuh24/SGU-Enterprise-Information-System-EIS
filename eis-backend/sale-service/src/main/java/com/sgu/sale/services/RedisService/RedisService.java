package com.sgu.sale.services.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService implements IRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Save data to Redis
    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Save with expiration
    @Override
    public void saveWithExpiration(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    // Retrieve data from Redis
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Delete data
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}

