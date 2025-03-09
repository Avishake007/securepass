package com.securepass.OrderService.repositories;

import com.securepass.OrderService.entity.OrderItems;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItems,String> {
}
