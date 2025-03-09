package com.securepass.InventoryService.services;

import com.securepass.common_library.dto.kafka.OrderEvent;

public interface InventoryKafkaListenerService {
	
	void consumeStocksOfProduct(OrderEvent orderEvent);
}
