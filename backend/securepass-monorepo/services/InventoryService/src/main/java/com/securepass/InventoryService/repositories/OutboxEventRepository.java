package com.securepass.InventoryService.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.securepass.InventoryService.entities.OutboxEvent;

@Repository
public interface OutboxEventRepository extends MongoRepository<OutboxEvent, Long> {
}
