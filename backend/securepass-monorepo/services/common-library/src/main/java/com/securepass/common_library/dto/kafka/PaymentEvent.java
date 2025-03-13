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
public class PaymentEvent {
	
	private String orderId;
	
	private List<OrderItemRequestDto> orderItems; 
}
