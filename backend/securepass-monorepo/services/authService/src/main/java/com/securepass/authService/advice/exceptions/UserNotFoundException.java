package com.securepass.authService.advice.exceptions;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
		
	}
}
