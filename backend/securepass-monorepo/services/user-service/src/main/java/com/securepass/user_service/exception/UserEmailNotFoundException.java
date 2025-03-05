package com.securepass.user_service.exception;

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
