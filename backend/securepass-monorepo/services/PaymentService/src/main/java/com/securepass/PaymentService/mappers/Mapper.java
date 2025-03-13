package com.securepass.PaymentService.mappers;

import java.util.Date;

import com.securepass.PaymentService.entities.Payment;
import com.securepass.PaymentService.enums.PaymentMethod;
import com.securepass.PaymentService.enums.PaymentStatus;
import com.securepass.common_library.dto.kafka.ProductEvent;
import com.securepass.common_library.dto.payment.PaymentRequestDto;

public class Mapper {
	
	public static Payment mapPaymentRequestDtoToPayment(PaymentRequestDto paymentRequestDto) {
		return Payment
				.builder()
				.order_id(paymentRequestDto.getOrder_id())
				.user_id(paymentRequestDto.getUser_id())
				.amount(paymentRequestDto.getAmount())
				.createdAt(new Date())
				.paymentMethod(PaymentMethod.CREDIT_CARD)
				.status(PaymentStatus.PENDING)
				.build();
	}
	
	public static PaymentRequestDto mapProductEventToPaymentRequestDto(ProductEvent productEvent) {
		
		return PaymentRequestDto
				.builder()
				.order_id(productEvent.getOrderId())
				.user_id(productEvent.getUserId())
				.amount(productEvent.getPaymentAmount())
				.paymentMethod(com.securepass.common_library.enums.PaymentMethod.DEBIT_CARD)
				.build();
	}
}
