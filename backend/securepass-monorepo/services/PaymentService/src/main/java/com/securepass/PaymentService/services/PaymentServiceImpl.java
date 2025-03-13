package com.securepass.PaymentService.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securepass.PaymentService.constants.KafkaPaymentConstants;
import com.securepass.PaymentService.exceptions.PaymentNotInitiatedException;
import com.securepass.PaymentService.mappers.Mapper;
import com.securepass.PaymentService.repository.PaymentRepository;
import com.securepass.common_library.dto.kafka.PaymentEvent;
import com.securepass.common_library.dto.kafka.ProductEvent;
import com.securepass.common_library.dto.payment.BasePaymentResponseDto;
import com.securepass.common_library.dto.payment.PaymentRequestDto;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentRepository paymentRepository;
	
	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;

	}
	
	@Transactional
	@Override
	public BasePaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) throws PaymentNotInitiatedException{
		
		try {
			System.out.println("iop1");
			throw new PaymentNotInitiatedException("Payment not initiated");
			//paymentRepository.save(Mapper.mapPaymentRequestDtoToPayment(paymentRequestDto));
			//System.out.println("iop2");
			
		}
		catch(Exception e) {
			throw new PaymentNotInitiatedException("Payment not initiated");
		}
		
		
		
		//return new BasePaymentResponseDto("0","SUCCESS");
		
	}

	
}
