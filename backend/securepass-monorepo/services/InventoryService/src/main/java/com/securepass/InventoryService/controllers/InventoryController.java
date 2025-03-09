package com.securepass.InventoryService.controllers;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.dtos.InventoryResponseDto;
import com.securepass.InventoryService.services.InventoryService;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	@PostMapping
	public ResponseEntity<BaseInventoryResponseDto> createInventory(@RequestBody InventoryRequestDto inventoryRequestDto) {
		
		
		return ResponseEntity
				.status(HttpStatus.SC_CREATED)
				.body(inventoryService.createOrder(inventoryRequestDto));
	}
	
	@PatchMapping("/{productId}/reduce/{units}")
	public ResponseEntity<BaseInventoryResponseDto> reduceStocksByUnits(
			@PathVariable("productId") String productId,
			@PathVariable("units") int units ){
		
		;
		
		return ResponseEntity
				.status(HttpStatus.SC_ACCEPTED)
				.body(inventoryService.reduceStocksByUnits(productId, units));

		
	}
	
	
//	/inventory/{productId}
//	/inventory/{productId}/reduce
//	/inventory/{productId}/increase
//	
//	/inventory/all
}
