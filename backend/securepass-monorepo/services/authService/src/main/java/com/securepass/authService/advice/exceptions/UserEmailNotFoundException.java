package com.securepass.authService.advice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEmailNotFoundException extends RuntimeException{
	
	public UserEmailNotFoundException(String message) {
		super(message);
	}
	
}
