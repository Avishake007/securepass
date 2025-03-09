package com.securepass.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {

    @Id
    @Field(name = "id")
    private String order_items_id;

    @Field(name = "orderId")
    private  String order_id;

    @Field(name = "productId")
    private String product_id;

    @Field(name = "productQuantity")
    private int quantity;

    @Field(name = "productPrice")
    private double price;


}
