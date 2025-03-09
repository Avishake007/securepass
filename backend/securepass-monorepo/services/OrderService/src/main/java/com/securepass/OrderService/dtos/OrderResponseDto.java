package com.securepass.OrderService.dtos;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securepass.OrderService.enums.OrderStatus;
import com.securepass.common_library.dto.OrderItemRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderResponseDto extends BaseOrderReponse{
	
    private String order_id;

    
    private String user_id;

   
    private double total_amount;

    
    private OrderStatus order_status;
    
    @JsonProperty("orderItems")
    List<OrderItemRequestDto> orderItems;

    

}
