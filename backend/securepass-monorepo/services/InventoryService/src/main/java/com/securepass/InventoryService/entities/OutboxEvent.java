package com.securepass.InventoryService.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document("outbox_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxEvent {

    @Id
    private Long id;

    private String aggregateType; // Entity type, e.g., "Order"
    private String aggregateId;   // Entity ID, e.g., Order ID
    private String eventType;     // Event type, e.g., "ORDER_CREATED"

    private String payload; // JSON payload of the event

    private LocalDateTime createdAt = LocalDateTime.now();

   
}
