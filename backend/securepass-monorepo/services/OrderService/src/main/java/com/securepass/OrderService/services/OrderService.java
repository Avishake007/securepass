package com.securepass.OrderService.services;

import com.securepass.OrderService.dtos.BaseOrderReponse;
import com.securepass.OrderService.dtos.OrderRequestDto;
import com.securepass.OrderService.dtos.OrderResponseDto;

public interface OrderService {

    public BaseOrderReponse createOrder(OrderRequestDto orderRequestDto);
    
    public <T> T getOrderByOrderId(String orderId);
}
