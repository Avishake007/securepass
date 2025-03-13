package com.securepass.PaymentService.services;

import com.securepass.common_library.dto.kafka.ProductEvent;

public interface PaymentKafkaListenerService {
	
	void initiatePayment(ProductEvent productEvent);
}
