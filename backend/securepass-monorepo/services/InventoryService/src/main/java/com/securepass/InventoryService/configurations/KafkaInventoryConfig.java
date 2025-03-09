package com.securepass.InventoryService.configurations;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.securepass.InventoryService.constants.InventoryKafkaConstants;



@Configuration
public class KafkaInventoryConfig {
	
	
	public NewTopic orderInitiatedTopic(){
        return TopicBuilder
                .name(InventoryKafkaConstants.PRODUCT_ALLOCATION_FAILED)
                .partitions(1)
                .replicas(1)
                .build();
    }


}