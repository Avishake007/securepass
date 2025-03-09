package com.securepass.InventoryService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.securepass.InventoryService.constants.InventoryKafkaConstants;
import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.dtos.InventoryResponseDto;
import com.securepass.InventoryService.entities.Inventory;
import com.securepass.InventoryService.mappers.Mapper;
import com.securepass.InventoryService.repositories.InventoryRepository;
import com.securepass.common_library.dto.kafka.ProductEvent;

@Service
public class InventoryServiceImpl implements InventoryService{
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	
	
	

	@Override
	public BaseInventoryResponseDto createOrder(InventoryRequestDto inventoryRequestDto) {
		
		Inventory inventory = Mapper.mapInventoryRequestDtoToInventory(inventoryRequestDto);
		inventoryRepository.save(inventory);
		
		return BaseInventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("Inventory created successfully")
				.build();
		
	}
	
	public InventoryResponseDto getInventoryById(String productId) {
		
		Inventory currInventory = inventoryRepository.findById(productId).orElseThrow();
		
		return InventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("Fetch product details successfully")
				.inventory(currInventory)
				.build();
		
	}
	
	

	@Override
	public BaseInventoryResponseDto reduceStocksByUnits( String productId, int units) {
		
		Inventory inventory = getInventoryById(productId).getInventory();
		
		int currStocks = inventory.getStock();
		
		if(currStocks >= units) {
			inventory.setStock(currStocks - units);
		}
		else {
		
			return null;
		}
		
		inventoryRepository.save(inventory);
		
		return BaseInventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("Inventory updated successfully")
				.build();
		
	}

	@Override
	public BaseInventoryResponseDto increaseStocksByUnits(String productId, int units) {
		Inventory inventory = getInventoryById(productId).getInventory();
		
		int currStocks = inventory.getStock();
		inventory.setStock(currStocks + units);
		
		inventoryRepository.save(inventory);
		
		return BaseInventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("Inventory updated successfully")
				.build();
	}

}
