package com.securepass.InventoryService.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory implements Serializable{

	@Id
	private String id;
	
	private String name;
	
	private double price;
	
	private int stock;
	
	private String category;
	
	private Date createdAt;
}
