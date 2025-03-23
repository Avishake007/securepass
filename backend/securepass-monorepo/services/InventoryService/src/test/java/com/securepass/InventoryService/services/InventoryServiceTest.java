package com.securepass.InventoryService.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.dtos.InventoryResponseDto;
import com.securepass.InventoryService.entities.Inventory;
import com.securepass.InventoryService.exceptions.InventoryNotFoundException;
import com.securepass.InventoryService.mappers.Mapper;
import com.securepass.InventoryService.repositories.InventoryRepository;
import com.securepass.InventoryService.services.implementation.InventoryServiceImpl;
import com.securepass.InventoryService.services.specification.InventoryService;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

	@Mock
	InventoryRepository inventoryRepository;
	
	@InjectMocks
	InventoryServiceImpl inventoryService;
	
	InventoryRequestDto inventoryRequestDto;
	
	InventoryResponseDto inventoryResponseDto;
	
	Inventory inventory;
	
	
	BaseInventoryResponseDto baseInventoryResponseDto;
	
	@BeforeEach
	public void setup(){
		inventoryRequestDto = 
				inventoryRequestDto
				.builder()
				.name("Samsung Galaxy")
				.price(10000)
				.stock(5)
				.category("Mobile Phone")
				.build();
		
		
		inventory =
				inventory
				.builder()
				.id("123")
				.name("Samsung Galaxy")
				.price(10000)
				.stock(5)
				.category("Mobile Phone")
				.createdAt(new Date())
				.build();
		
		baseInventoryResponseDto = BaseInventoryResponseDto
		.builder()
		.responseCode("0")
		.responseStatus("Inventory created successfully")
		.build();
		
		inventoryResponseDto =
				inventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("Fetch product details successfully")
				.inventory(inventory)
				.build();
		
	}
	
	@Test
	@DisplayName("Test 1 : shouldCreateOrderTest")
	public void shouldCreateOrderTest() {
	
		//when(Mapper.mapInventoryRequestDtoToInventory(inventoryRequestDto)).thenReturn(inventory);
		
		//when(inventoryRepository.save(inventory)).thenReturn(inventory);
		
		BaseInventoryResponseDto actualBaseInventoryResponseDto = inventoryService.createOrder(inventoryRequestDto);
		
		assertEquals(baseInventoryResponseDto.getResponseCode(), actualBaseInventoryResponseDto.getResponseCode());
		
		assertEquals(baseInventoryResponseDto.getResponseStatus(), actualBaseInventoryResponseDto.getResponseStatus());
		
	}
	
	@Test
	@DisplayName("Test 2 : shouldGetInventoryById")
	public void shouldGetInventoryById() {
		when(inventoryRepository.findById("123")).thenReturn(Optional.of(inventory));
		
		InventoryResponseDto currInventoryResponseDto = inventoryService.getInventoryById("123");
		assertEquals(inventoryResponseDto.getResponseCode(), currInventoryResponseDto.getResponseCode());
		assertEquals(inventoryResponseDto.getResponseStatus(), currInventoryResponseDto.getResponseStatus());
	}
	
	
	@Test
	@DisplayName("Test 3 : shouldThrowErrorGetInventoryById")
	public void shouldThrowErrorGetInventoryById() {
		when(inventoryRepository.findById("1234")).thenReturn(Optional.empty());
		
		
		
		assertThrows(InventoryNotFoundException.class, () ->inventoryService.getInventoryById("1234"));
	}
	
}
