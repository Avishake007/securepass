package com.securepass.InventoryService.dtos;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDto {

	private String name;
	
	private double price;
	
	private int stock;
	
	private String category;
	
}
