package com.securepass.InventoryService.services.specification;

import java.util.List;

import com.securepass.InventoryService.dtos.AllInventoryResponseDto;
import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.dtos.InventoryResponseDto;

public interface InventoryService {

	BaseInventoryResponseDto createOrder(InventoryRequestDto inventoryRequestDto);
	
	BaseInventoryResponseDto reduceStocksByUnits(String productId, int units);
	
	BaseInventoryResponseDto increaseStocksByUnits(String productId, int units);
	
	InventoryResponseDto getInventoryById(String productId);
	
	AllInventoryResponseDto getAllInventories();
}
