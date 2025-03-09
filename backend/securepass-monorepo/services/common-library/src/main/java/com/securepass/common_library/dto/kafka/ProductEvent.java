package com.securepass.common_library.dto.kafka;

import java.util.List;

import com.securepass.common_library.dto.OrderItemRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEvent {

	private String orderId;
}
