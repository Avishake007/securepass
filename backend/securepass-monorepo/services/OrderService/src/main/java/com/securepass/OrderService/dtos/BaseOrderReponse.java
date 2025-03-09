package com.securepass.OrderService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securepass.OrderService.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseOrderReponse {

    @JsonProperty("responseCode")
    String response_code;

    @JsonProperty("responseStatus")
    String response_status;


}
