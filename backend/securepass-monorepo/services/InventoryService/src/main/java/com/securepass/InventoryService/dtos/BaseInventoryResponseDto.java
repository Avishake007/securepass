package com.securepass.InventoryService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseInventoryResponseDto {

	private String responseCode;
	
	private String responseStatus;
}
