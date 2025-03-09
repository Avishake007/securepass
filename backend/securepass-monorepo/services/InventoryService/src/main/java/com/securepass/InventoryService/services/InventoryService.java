package com.securepass.InventoryService.services;

import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.dtos.InventoryResponseDto;

public interface InventoryService {

	BaseInventoryResponseDto createOrder(InventoryRequestDto inventoryRequestDto);
	
	BaseInventoryResponseDto reduceStocksByUnits(String productId, int units);
	
	BaseInventoryResponseDto increaseStocksByUnits(String productId, int units);
}
