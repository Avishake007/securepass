package com.securepass.InventoryService.dtos;

import java.io.Serializable;
import java.util.Date;

import com.securepass.InventoryService.entities.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InventoryResponseDto extends BaseInventoryResponseDto implements Serializable{
	
	private Inventory inventory;
	
	

}
