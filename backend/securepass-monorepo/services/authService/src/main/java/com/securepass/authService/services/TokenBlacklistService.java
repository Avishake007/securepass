package com.securepass.authService.services;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final long EXPIRATION_TIME = 3600; // 1 Hour

    public void blacklistToken(String token) {
        redisTemplate.opsForValue().set(token, "blacklisted", EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(token);
    }
}
