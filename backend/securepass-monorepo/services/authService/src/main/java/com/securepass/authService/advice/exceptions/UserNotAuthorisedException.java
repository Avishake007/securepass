package com.securepass.authService.advice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserNotAuthorisedException extends RuntimeException{
	public UserNotAuthorisedException(String message) {
		super(message);
	}
}
