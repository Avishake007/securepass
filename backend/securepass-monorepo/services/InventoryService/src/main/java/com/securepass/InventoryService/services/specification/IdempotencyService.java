package com.securepass.InventoryService.services.specification;

import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;

public interface IdempotencyService {
	
	void saveResponse(String idempotencyKey, BaseInventoryResponseDto response);
	boolean isDuplicateRequest(String idempotencyKey);
	BaseInventoryResponseDto getPreviousResponse(String idempotencyKey);
	
}
