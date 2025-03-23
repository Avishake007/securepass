package com.securepass.InventoryService.dtos;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestDto {

	@JsonProperty("inventoryName")
	private String name;
	
	private double price;
	
	private int stock;
	
	private String category;
	
}
