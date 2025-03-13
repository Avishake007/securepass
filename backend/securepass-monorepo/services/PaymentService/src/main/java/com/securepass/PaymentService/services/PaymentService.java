package com.securepass.PaymentService.services;

import com.securepass.common_library.dto.payment.BasePaymentResponseDto;
import com.securepass.common_library.dto.payment.PaymentRequestDto;

public interface PaymentService {
	BasePaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
}
