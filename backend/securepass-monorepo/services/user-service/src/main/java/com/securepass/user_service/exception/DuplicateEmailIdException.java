package com.securepass.user_service.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DuplicateEmailIdException extends RuntimeException{
	
	public DuplicateEmailIdException(String message) {
		super(message);
	}
}
