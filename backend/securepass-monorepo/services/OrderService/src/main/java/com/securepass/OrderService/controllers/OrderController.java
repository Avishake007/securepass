package com.securepass.OrderService.controllers;

import com.securepass.OrderService.dtos.BaseOrderReponse;
import com.securepass.OrderService.dtos.OrderRequestDto;
import com.securepass.OrderService.dtos.OrderResponseDto;
import com.securepass.OrderService.entity.Order;
import com.securepass.OrderService.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<BaseOrderReponse> addOrder(@RequestBody OrderRequestDto orderRequestDto){


        return ResponseEntity.status(HttpStatus.CREATED).body( orderService.createOrder(orderRequestDto));

    }
   @GetMapping("/{orderId}")
   public ResponseEntity<Object> getOrderByOrderId(@PathVariable("orderId") String orderId){
	   return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByOrderId(orderId));
   }
   
  
  
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<>
//    /orders/{orderId}/cancel
//    /orders/{orderId}/status
}
