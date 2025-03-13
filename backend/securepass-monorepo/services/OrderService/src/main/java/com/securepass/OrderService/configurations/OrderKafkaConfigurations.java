package com.securepass.OrderService.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.securepass.OrderService.constants.OrderKafkaConstants;
import com.securepass.common_library.dto.kafka.OrderEvent;

@Configuration
public class OrderKafkaConfigurations {
	
	
	KafkaTemplate<String, OrderEvent> kafkaTemplate(ProducerFactory<String, OrderEvent> producerFactory){
		KafkaTemplate<String, OrderEvent> template = new KafkaTemplate<>(producerFactory);
		template.setTransactionIdPrefix("order-producer");
		
		return template;
	}
	
	
	
	NewTopic orderInitiatedTopic(){
        return TopicBuilder
                .name(OrderKafkaConstants.ORDER_INITIATED)
                .partitions(1)
                .replicas(1)
                .build();
    }


}
