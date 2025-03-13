package com.securepass.OrderService.entity;

import com.securepass.OrderService.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document("order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Order {

    @Id
    @Field("orderId")
    private ObjectId  order_id;

    @Field(name = "userId")
    private String user_id;

    @Field(name = "totalAmount")
    private double total_amount;

    @Field(name = "order-status")
    private OrderStatus order_status;

    @Field(name = "createdDate")
    private Date created_date;

    @Field(name = "updatedDate")
    private Date updated_date;

}


