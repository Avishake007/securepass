package com.securepass.OrderService.repositories;

import com.securepass.OrderService.entity.Order;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends MongoRepository<Order, ObjectId> {
	
	@Query("{'_id' : ?0}")
	Optional<Order> findByObjectId(ObjectId id);
}
