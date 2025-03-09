package com.securepass.OrderService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {
	
	private String orderItemsId;
	
	private String product_id;

    private int quantity;

    private double price;
}
