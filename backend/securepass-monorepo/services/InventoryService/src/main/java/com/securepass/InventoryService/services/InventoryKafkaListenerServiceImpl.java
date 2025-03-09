package com.securepass.InventoryService.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.securepass.InventoryService.constants.InventoryKafkaConstants;
import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.common_library.dto.OrderItemRequestDto;
import com.securepass.common_library.dto.kafka.OrderEvent;
import com.securepass.common_library.dto.kafka.ProductEvent;

@Service
public class InventoryKafkaListenerServiceImpl implements InventoryKafkaListenerService{
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	KafkaTemplate< String,ProductEvent> template;

	@Override
	@KafkaListener(topics = InventoryKafkaConstants.ORDER_INITIATED, groupId = InventoryKafkaConstants.ORDER_GROUP)
	public void consumeStocksOfProduct(OrderEvent orderEvent) {
		System.out.println("Order initiated");
		
		int backTrackInd = -1;
		int cnt = -1;
		
		for(OrderItemRequestDto orderItem : orderEvent.getOrderItems()) {
			System.out.println(orderItem.getProductId());
			BaseInventoryResponseDto baseInventoryResponseDto = inventoryService.reduceStocksByUnits( orderItem.getProductId(), orderItem.getQuantity());
			System.out.println("BaseInventoryResponseDto "+baseInventoryResponseDto);
			if(baseInventoryResponseDto == null) {
				this.template.send(
						InventoryKafkaConstants.PRODUCT_ALLOCATION_FAILED,
						ProductEvent
						.builder()
						.orderId(orderEvent.getOrderId())
						.build()
						);
				backTrackInd = cnt;
				break;
			}
			cnt ++;
		}
		
		while(backTrackInd != -1) {
			OrderItemRequestDto currOrderItemRequestDto = orderEvent.getOrderItems().get(backTrackInd);
			inventoryService.increaseStocksByUnits(currOrderItemRequestDto.getProductId(), currOrderItemRequestDto.getQuantity());
			backTrackInd --;
		}
		
	}
}
