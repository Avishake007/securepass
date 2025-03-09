package com.securepass.OrderService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securepass.OrderService.entity.OrderItems;
import com.securepass.OrderService.enums.OrderStatus;
import com.securepass.common_library.dto.OrderItemRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    @JsonProperty("userId")
    private String user_id;

    @JsonProperty("totalAmount")
    private double total_amount;
    
    @JsonProperty("orderItems")
    List<OrderItemRequestDto> orderItems;
    

}
