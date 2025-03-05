package com.securepass.authService.advice.exceptions;

import lombok.Data;

@Data
public class CustomFilterException extends RuntimeException{
	
	int statusCode;
	
	public CustomFilterException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}
	
	 public int getStatusCode() {
	        return this.statusCode;
	    }
}
