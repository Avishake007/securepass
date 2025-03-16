package com.securepass.InventoryService.mappers;

import java.util.Date;

import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.entities.Inventory;


public class Mapper {
	
	public static Inventory mapInventoryRequestDtoToInventory(InventoryRequestDto inventoryRequestDto) {
		return Inventory
				.builder()
				.name(inventoryRequestDto.getName())
				.price(inventoryRequestDto.getPrice())
				.stock(inventoryRequestDto.getStock())
				.category(inventoryRequestDto.getCategory())
				.createdAt(new Date())
				.build();
	}
	
	//public static 
}
