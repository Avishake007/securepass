package com.securepass.InventoryService.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.securepass.InventoryService.constants.InventoryKafkaConstants;
import com.securepass.InventoryService.entities.OutboxEvent;
import com.securepass.InventoryService.repositories.OutboxEventRepository;
import com.securepass.common_library.dto.kafka.ProductEvent;

@Component
public class OutboxEventPublisher {

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Autowired
	private KafkaTemplate< String,Object> template;
    
    @Transactional
    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    public void publishEvents() {
    	System.out.println("Outbox Event Publisher");
        List<OutboxEvent> events = outboxEventRepository.findAll();

        for (OutboxEvent event : events) {
        	this.template.send(
        			event.getAggregateType(),
        			event.getPayload()
					);

            outboxEventRepository.delete(event); // Mark as processed
        }
    }
}
