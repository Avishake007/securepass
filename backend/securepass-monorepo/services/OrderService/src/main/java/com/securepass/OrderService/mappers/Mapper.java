package com.securepass.OrderService.mappers;

import com.securepass.OrderService.dtos.OrderRequestDto;
import com.securepass.OrderService.entity.Order;
import com.securepass.OrderService.entity.OrderItems;
import com.securepass.OrderService.enums.OrderStatus;
import com.securepass.common_library.dto.OrderItemRequestDto;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Mapper {

    public static Order mapOrderRequestDtoToOrderMapper(OrderRequestDto orderRequestDto){

        Date date =  new Date();

         return Order
                 .builder()
                .order_status(OrderStatus.PENDING)
                .user_id(orderRequestDto.getUser_id())
                .total_amount(orderRequestDto.getTotal_amount())
                .created_date(date)
                .updated_date(date)
                .build();

        // return new Order(null, orderRequestDto.getUser_id(), orderRequestDto.getTotal_amount(),OrderStatus.PENDING,date, date);
    }

    public static List<OrderItems> mapListOfOrderItemRequestDtoToListOfOrderItem(
            List<OrderItemRequestDto> orderItemRequestDtos,
            ObjectId orderId)
    {

       List<OrderItems> orderItemsLi = new ArrayList<>();

       for(OrderItemRequestDto orderItemRequestDto : orderItemRequestDtos){
           orderItemsLi
                   .add(OrderItems
                           .builder()
                           .order_id(orderId.toString())
                           .price(orderItemRequestDto.getPrice())
                           .quantity(orderItemRequestDto.getQuantity())
                           .product_id(orderItemRequestDto.getProductId())
                            .build());
       }

       return orderItemsLi;


    }


}
