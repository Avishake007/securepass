package com.securepass.InventoryService.services;

import com.securepass.common_library.dto.kafka.OrderEvent;
import com.securepass.common_library.dto.kafka.PaymentEvent;

public interface InventoryKafkaListenerService {
	
	void consumeStocksOfProduct(OrderEvent orderEvent);
	
	void rollbackInventoryStocks(PaymentEvent paymentEvent);
}
