package com.securepass.PaymentService.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.securepass.PaymentService.constants.KafkaPaymentConstants;
import com.securepass.PaymentService.mappers.Mapper;
import com.securepass.common_library.dto.kafka.PaymentEvent;
import com.securepass.common_library.dto.kafka.ProductEvent;

@Service
public class PaymentKafkaListenerServiceImpl implements PaymentKafkaListenerService{
	
	private final PaymentService paymentService;
	

	private final KafkaTemplate<String, PaymentEvent> kafkaTemplateFailed;
	
	private final KafkaTemplate<String, String> kafkaTemplateSucceeded;
	
	
	public PaymentKafkaListenerServiceImpl(PaymentService paymentService ,  KafkaTemplate<String, PaymentEvent> kafkaTemplateFailed, KafkaTemplate<String, String> kafkaTemplateSucceeded) {
		this.paymentService = paymentService;
		this.kafkaTemplateFailed = kafkaTemplateFailed;
		this.kafkaTemplateSucceeded = kafkaTemplateSucceeded;
	}

	@Override
	@KafkaListener(topics = KafkaPaymentConstants.PRODUCT_ALLOCATION_SUCCEDDED , groupId = KafkaPaymentConstants.PAYMENT_GROUP)
	public void initiatePayment(ProductEvent productEvent) {
		try {
		
		paymentService.createPayment(Mapper.mapProductEventToPaymentRequestDto(productEvent));
		this.kafkaTemplateSucceeded.send(KafkaPaymentConstants.PAYMENT_SUCCEEDED, "Payment Failed");
		}
		catch(Exception e) {
			this.kafkaTemplateFailed.send(KafkaPaymentConstants.PAYMENT_FAILED, 
					PaymentEvent
					.builder()
					.orderId(productEvent.getOrderId())
					.orderItems(productEvent.getOrderItems())
					.build()
					);
		}
		
		
	}

}
