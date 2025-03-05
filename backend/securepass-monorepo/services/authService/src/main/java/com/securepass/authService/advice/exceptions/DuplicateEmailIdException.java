package com.securepass.authService.advice.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DuplicateEmailIdException extends RuntimeException{
	
	public DuplicateEmailIdException(String message) {
		super(message);
	}
}
