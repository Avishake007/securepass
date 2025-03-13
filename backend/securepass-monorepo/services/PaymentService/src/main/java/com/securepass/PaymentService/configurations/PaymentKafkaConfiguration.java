package com.securepass.PaymentService.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.securepass.PaymentService.constants.KafkaPaymentConstants;



@Configuration
public class PaymentKafkaConfiguration {
	
	
	NewTopic paymentIntiateTopicSucceeded() {
		return TopicBuilder
		 .name(KafkaPaymentConstants.PAYMENT_SUCCEEDED)
         .partitions(1)
         .replicas(1)
         .build();
	}
	
	NewTopic paymentIntiateTopicFailed() {
		return TopicBuilder
		 .name(KafkaPaymentConstants.PAYMENT_FAILED)
         .partitions(2)
         .replicas(1)
         .build();
	}
}
