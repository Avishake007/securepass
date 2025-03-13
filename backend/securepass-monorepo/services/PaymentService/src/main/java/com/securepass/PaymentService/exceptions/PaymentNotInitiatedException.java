package com.securepass.PaymentService.exceptions;

public class PaymentNotInitiatedException extends RuntimeException{
	
	public PaymentNotInitiatedException(String message) {
		super(message);
	}

}
