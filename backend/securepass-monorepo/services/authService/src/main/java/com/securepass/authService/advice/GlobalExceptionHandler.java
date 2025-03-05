package com.securepass.authService.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.securepass.authService.advice.exceptions.DuplicateEmailIdException;
import com.securepass.authService.advice.exceptions.UserNotAuthorisedException;
import com.securepass.authService.advice.exceptions.UserNotFoundException;
import com.securepass.common_library.dto.BaseResponse;

import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;


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
	
	@ExceptionHandler({FeignException.class})
	public ResponseEntity<BaseResponse> feignExceptionHandler(Exception e) {
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("402", "BAD REQUEST"));
	}
	
	@ExceptionHandler({ExpiredJwtException.class})
	public ResponseEntity<BaseResponse> handleExpiredJsonException(Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("402", "Token is expired"));
	}

}