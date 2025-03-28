package com.securepass.InventoryService.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseInventoryResponseDto implements Serializable{

	private String responseCode;
	
	private String responseStatus;
}
