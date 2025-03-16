package com.securepass.InventoryService.services.implementation;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.common.record.TimestampType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.services.specification.IdempotencyService;

@Service
public class IdempotencyServiceImpl implements IdempotencyService{

	private final RedisTemplate<String, BaseInventoryResponseDto> redisTemplate;
	
	private final long EXPIRATION_MINUTES = 10;
	
	public IdempotencyServiceImpl(RedisTemplate<String, BaseInventoryResponseDto> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public void saveResponse(String idempotencyKey, BaseInventoryResponseDto response) {
		redisTemplate.opsForValue().set(idempotencyKey, response,EXPIRATION_MINUTES, TimeUnit.MINUTES);
		
	}

	@Override
	public boolean isDuplicateRequest(String idempotencyKey) {
		
		return redisTemplate.hasKey(idempotencyKey);
	}

	@Override
	public BaseInventoryResponseDto getPreviousResponse(String idempotencyKey) {
		
		 return redisTemplate.opsForValue().get(idempotencyKey);
	}

}
