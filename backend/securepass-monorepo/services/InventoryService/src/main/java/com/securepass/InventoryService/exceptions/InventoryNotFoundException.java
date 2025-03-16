package com.securepass.InventoryService.exceptions;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class InventoryNotFoundException extends RuntimeException{
	
	public InventoryNotFoundException(String message) {
		super(message);
	}
}
