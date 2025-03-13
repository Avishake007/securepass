package com.securepass.common_library.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securepass.common_library.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

	@JsonProperty("orderId")
	private String order_id;
	
	@JsonProperty("userId")
	private String user_id;
	
	@JsonProperty("paymentAmount")
	private double amount;
	
	
	@JsonProperty("paymentMethod")
	private PaymentMethod paymentMethod;
}
