package com.securepass.PaymentService.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securepass.PaymentService.enums.PaymentMethod;
import com.securepass.PaymentService.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("orderId")
	private String order_id;
	
	@JsonProperty("userId")
	private String user_id;
	
	@JsonProperty("paymentAmount")
	private double amount;
	
	@JsonProperty("paymentStatus")
	private PaymentStatus status;
	
	@JsonProperty("paymentMethod")
	private PaymentMethod paymentMethod;
	
	@JsonProperty("paymentCreatedDate")
	private Date createdAt;
}
