package com.securepass.OrderService.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.reactive.TransactionSynchronizationManager;
//import org.springframework.transaction.reactive.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronization;
//import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.securepass.OrderService.constants.OrderKafkaConstants;
import com.securepass.OrderService.dtos.BaseOrderReponse;
import com.securepass.OrderService.dtos.OrderRequestDto;
import com.securepass.OrderService.dtos.OrderResponseDto;
import com.securepass.OrderService.entity.Order;
import com.securepass.OrderService.enums.OrderStatus;
import com.securepass.OrderService.mappers.Mapper;
import com.securepass.OrderService.repositories.OrderItemRepository;
import com.securepass.OrderService.repositories.OrderRepository;
import com.securepass.common_library.dto.kafka.OrderEvent;
import com.securepass.common_library.dto.kafka.ProductEvent;

@Service
public class OrderServiceImpl implements  OrderService{

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Autowired
    private MongoTemplate mongoTemplate;


    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, KafkaTemplate<String, Object> template){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.kafkaTemplate = template;
    }

    @Transactional
    @Override
    public BaseOrderReponse createOrder(OrderRequestDto orderRequestDto){

        Order order = orderRepository.save(Mapper.mapOrderRequestDtoToOrderMapper(orderRequestDto));

        orderItemRepository.saveAll(
        		Mapper.mapListOfOrderItemRequestDtoToListOfOrderItem(
        				orderRequestDto.getOrderItems(), 
        				order.getOrder_id()
        				)
        		);
        
        System.out.println(orderRequestDto.getOrderItems());
        
//        TransactionSynchronizationManager.registerSynchronization(
//        		new TransactionSynchronization()  {
//            
//
//			@Override
//            public void afterCommit() {
            	  kafkaTemplate.send(OrderKafkaConstants.ORDER_INITIATED,
                  		OrderEvent
                  		.builder()
                  		.orderId(order.getOrder_id().toString())
                  		.userId(order.getUser_id())
                  		.paymentAmount(order.getTotal_amount())
                  		.orderItems(orderRequestDto.getOrderItems())
                  		.build()
                  		);
//            }
//        });
        
      
        return new BaseOrderReponse("0","Order is created successfully");
        
        
    }

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOrderByOrderId(String orderId) {
		Order order = orderRepository.findByObjectId(new ObjectId(orderId)).orElse(null);
		
		return order != null ?
				(T) OrderResponseDto
				.builder()
				.response_code("0")
				.response_status("Order Fetched Successfully")
				.order_id(orderId)
				.user_id(order.getUser_id())
				.total_amount(order.getTotal_amount())
				.order_status(order.getOrder_status())
				.build() :
				(T) BaseOrderReponse.builder()
				.response_code("0")
				.response_status("No order of order id "+orderId+" is present")
				.build();
				
	}
	
	@Transactional
	//@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@KafkaListener(topics = OrderKafkaConstants.PRODUCT_ALLOCATION_FAILED, groupId = OrderKafkaConstants.ORDER_GROUP)
	public BaseOrderReponse rollbackOrderByOrderId(ProductEvent productEvent) {
		
		Order order = orderRepository.findByObjectId(new ObjectId(productEvent.getOrderId())).orElse(null);
		
		order.setOrder_status(OrderStatus.CANCELLED);
		
		orderRepository.save(order);
		
		System.out.println("deleted");
		 return new BaseOrderReponse("0","Order failed"); 
		//throw new RuntimeException();
	}
	
}
