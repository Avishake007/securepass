package com.securepass.InventoryService.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.exceptions.InventoryNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({InventoryNotFoundException.class})
	public ResponseEntity<BaseInventoryResponseDto> handleInventoryNotFoundException(Exception e){
		return ResponseEntity
				.status(404)
				.body
				(
				BaseInventoryResponseDto
				.builder()
				.responseCode("404")
				.responseStatus(e.getMessage())
				.build()
				);
	}
}
