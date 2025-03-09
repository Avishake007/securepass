package com.securepass.InventoryService.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.securepass.InventoryService.entities.Inventory;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String>{

}
