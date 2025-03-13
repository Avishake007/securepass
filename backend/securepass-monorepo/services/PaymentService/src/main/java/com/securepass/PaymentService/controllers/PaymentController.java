package com.securepass.PaymentService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securepass.PaymentService.services.PaymentService;
import com.securepass.common_library.dto.payment.BasePaymentResponseDto;
import com.securepass.common_library.dto.payment.PaymentRequestDto;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping
	public ResponseEntity<BasePaymentResponseDto> postPaymentDetails(@RequestBody PaymentRequestDto payment) {
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(payment));
		
		
	}
//	/payments/{paymentId}
//	/payments/order/{orderId}
	///payments/{paymentId}/refund
	///payments/verify
}
