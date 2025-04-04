package com.securepass.InventoryService.services.implementation;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.securepass.InventoryService.constants.InventoryKafkaConstants;
import com.securepass.InventoryService.dtos.AllInventoryResponseDto;
import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.dtos.InventoryResponseDto;
import com.securepass.InventoryService.entities.Inventory;
import com.securepass.InventoryService.entities.OutboxEvent;
import com.securepass.InventoryService.exceptions.InventoryNotFoundException;
import com.securepass.InventoryService.mappers.Mapper;
import com.securepass.InventoryService.repositories.InventoryRepository;
import com.securepass.InventoryService.repositories.OutboxEventRepository;
import com.securepass.InventoryService.services.specification.InventoryService;
import com.securepass.common_library.dto.kafka.ProductEvent;

@Service
public class InventoryServiceImpl implements InventoryService{
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	
	
	
	

	@Override
	@Transactional
	public BaseInventoryResponseDto createOrder(InventoryRequestDto inventoryRequestDto) {
		
		Inventory inventory = Mapper.mapInventoryRequestDtoToInventory(inventoryRequestDto);
		inventoryRepository.save(inventory);
		
		
		
		return BaseInventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("Inventory created successfully")
				.build();
		
	}
	
	@Override
	@Cacheable(value = "inventory", key="#id", condition = "#id != null")
	public InventoryResponseDto getInventoryById(String productId) throws InventoryNotFoundException{
		
		Inventory currInventory = inventoryRepository.findById(productId).orElseThrow(() -> new InventoryNotFoundException("Product with id "+ productId + " is not available"));
		
		return InventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("Fetch product details successfully")
				.inventory(currInventory)
				.build();
		
	}
	
	

	@Override
	@CachePut(cacheNames = "inventory")
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
	//@CachePut(value = {"inventory","inventories"})
	@Caching(evict = {@CacheEvict(cacheNames = "inventories" , allEntries = true)})
	public BaseInventoryResponseDto increaseStocksByUnits(String id, int units) {
		Inventory inventory = getInventoryById(id).getInventory();
		
		int currStocks = inventory.getStock();
		inventory.setStock(currStocks + units);
		
		inventoryRepository.save(inventory);
		
		return BaseInventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("Inventory updated successfully")
				.build();
	}

	@Override
	//@Cacheable(value = "inventories")
	public AllInventoryResponseDto getAllInventories() {
		
		List<Inventory> inventoryLi= inventoryRepository.findAll();
		
		return AllInventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus(inventoryLi.size() + " inventories fetched")
				.inventoryList(inventoryLi)
				.build();
		
		
		
	}

}
