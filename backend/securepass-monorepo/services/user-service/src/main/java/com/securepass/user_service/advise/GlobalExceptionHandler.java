package com.securepass.user_service.advise;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.securepass.common_library.dto.BaseResponse;
import com.securepass.user_service.exception.DuplicateEmailIdException;
import com.securepass.user_service.exception.UserNotAuthorisedException;
import com.securepass.user_service.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<BaseResponse> handleUserNotFoundException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse("404",e.getMessage()));
	}
	
	@ExceptionHandler({DuplicateEmailIdException.class})
	public ResponseEntity<BaseResponse> handleDuplicateEmailIdException(Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("400",e.getMessage()));
	}
	
	@ExceptionHandler({UserNotAuthorisedException.class})
	public ResponseEntity<BaseResponse> handleUserNotAuthorisedException(Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("400",e.getMessage()));
	}

	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		
		 Map<String, String> errors = new HashMap<String, String>();
	        e.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
