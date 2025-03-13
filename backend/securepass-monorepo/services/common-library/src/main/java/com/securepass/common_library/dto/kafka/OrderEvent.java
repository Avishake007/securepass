package com.securepass.common_library.dto.kafka;

import java.util.List;

import com.securepass.common_library.dto.OrderItemRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
	
	private String orderId;
	private String userId;
	private double paymentAmount;
	private List<OrderItemRequestDto> orderItems; 
}
