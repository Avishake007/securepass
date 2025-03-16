package com.securepass.InventoryService.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.securepass.InventoryService.constants.InventoryKafkaConstants;
import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.services.specification.InventoryKafkaListenerService;
import com.securepass.InventoryService.services.specification.InventoryService;
import com.securepass.common_library.dto.OrderItemRequestDto;
import com.securepass.common_library.dto.kafka.OrderEvent;
import com.securepass.common_library.dto.kafka.PaymentEvent;
import com.securepass.common_library.dto.kafka.ProductEvent;

@Service
public class InventoryKafkaListenerServiceImpl implements InventoryKafkaListenerService{
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	KafkaTemplate< String,Object> template;

	@Override
	@KafkaListener(topics = InventoryKafkaConstants.ORDER_INITIATED, groupId = InventoryKafkaConstants.INVENTORY_GROUP)
	public void consumeStocksOfProduct(OrderEvent orderEvent) {
		System.out.println("Order initiated");
		
		int backTrackInd = -1;
		int cnt = -1;
		
		boolean isSuccess = true;
		
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
				isSuccess = false;
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
		
		if(isSuccess == true) {
			System.out.println("Inv");
			template.send(
					InventoryKafkaConstants.PRODUCT_ALLOCATION_SUCCEDDED,
					ProductEvent
					.builder()
					.userId(orderEvent.getUserId())
					.orderId(orderEvent.getOrderId())
					.paymentAmount(orderEvent.getPaymentAmount())
					.orderItems(orderEvent.getOrderItems())
					.build()
					);
		}
		
	}

	@Override
	@KafkaListener(topics = InventoryKafkaConstants.PAYMENT_FAILED, groupId = InventoryKafkaConstants.INVENTORY_GROUP)
	public void rollbackInventoryStocks(PaymentEvent paymentEvent) {
		System.out.println("payment failed");
		for(OrderItemRequestDto orderItemRequestDto : paymentEvent.getOrderItems()) {
			inventoryService.increaseStocksByUnits(orderItemRequestDto.getProductId(), orderItemRequestDto.getQuantity());
		}
		
		this.template.send(
				InventoryKafkaConstants.PRODUCT_ALLOCATION_FAILED,
				ProductEvent
				.builder()
				.orderId(paymentEvent.getOrderId())
				.build()
				);

		
		
	}
}
