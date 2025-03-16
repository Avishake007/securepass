package com.securepass.InventoryService.controllers;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securepass.InventoryService.dtos.AllInventoryResponseDto;
import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.dtos.InventoryResponseDto;
import com.securepass.InventoryService.services.specification.IdempotencyService;
import com.securepass.InventoryService.services.specification.InventoryService;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	IdempotencyService idempotencyService;
	
	@PostMapping
	public ResponseEntity<BaseInventoryResponseDto> createInventory(@RequestHeader(name = "idempotent-key", required = false) String idempotentKey, @RequestBody InventoryRequestDto inventoryRequestDto) {
		
		if(idempotentKey == null || idempotentKey.equals("")) {
			return ResponseEntity
					.status(HttpStatus.SC_BAD_REQUEST)
					.body(BaseInventoryResponseDto
							.builder()
							.responseCode("400")
							.responseStatus("Bad Request")
							.build());
		}
		
		if(idempotencyService.isDuplicateRequest(idempotentKey)) {
			return ResponseEntity
					.status(HttpStatus.SC_OK)
					.body(idempotencyService.getPreviousResponse(idempotentKey));
		}
		
		BaseInventoryResponseDto response = inventoryService.createOrder(inventoryRequestDto);
		
		idempotencyService.saveResponse(idempotentKey, response);
		System.out.println("Response : "+response);
		return ResponseEntity
				.status(org.springframework.http.HttpStatus.CREATED)
				.body(response);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<InventoryResponseDto> getInventoryByProduct(@PathVariable("productId") String productId){
		return ResponseEntity
				.status(HttpStatus.SC_OK)
				.body(inventoryService.getInventoryById(productId));
	}
	
	@PatchMapping("/{productId}/reduce/{units}")
	public ResponseEntity<BaseInventoryResponseDto> reduceStocksByUnits(
			@PathVariable("productId") String productId,
			@PathVariable("units") int units ){
		
		
		
		return ResponseEntity
				.status(HttpStatus.SC_ACCEPTED)
				.body(inventoryService.reduceStocksByUnits(productId, units));

		
	}
	
	
	@PatchMapping("/{productId}/increase/{units}")
	public ResponseEntity<BaseInventoryResponseDto> increaseStocksByUnits(
			@PathVariable("productId") String productId,
			@PathVariable("units") int units ){
		
		
		
		return ResponseEntity
				.status(HttpStatus.SC_ACCEPTED)
				.body(inventoryService.increaseStocksByUnits(productId, units));

		
	}
	
	
	@GetMapping("/all")
	
	public ResponseEntity<AllInventoryResponseDto> getALlInventories(){
		return ResponseEntity
				.status(HttpStatus.SC_OK)
				.body(inventoryService.getAllInventories());
	}
 
}
