package com.securepass.authService.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.securepass.authService.external.dto.User;
import com.securepass.common_library.dto.BaseResponse;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.JwtTokenResponse;
import com.securepass.common_library.dto.UserLoginDetails;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.common_library.models.CurrentUser;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;



@FeignClient(name = "USERSERVICE")
public interface UserService {
	
	@PostMapping("/users/register")
	public BaseResponse registerUser(@RequestBody UserServiceRequest userServiceRequest);
	 
	 
	//@Retry(name ="getLoggedInUserDetailRetry", fallbackMethod = "getLoggedInUserDetailFallback")
	 //@RateLimiter(name = "getLoggedInUserDetailRateLimiter",fallbackMethod = "getLoggedInUserDetailFallback")
	 @GetMapping("/users/emailId/{emailId}")
	 @CircuitBreaker(name = "getLoggedInUserDetailBreaker", fallbackMethod = "getLoggedInUserDetailFallback")
	 public CurrUserDetailResponse getUserThroughEmailId(@PathVariable("emailId") String emailId);
	 
//	 default CurrUserDetailResponse getLoggedInUserDetailFallback(String token, Exception ex) {
//			return new CurrUserDetailResponse("0","User Service is currently down",null);
//		}
	
	default CurrUserDetailResponse getLoggedInUserDetailFallback(String emailId, Throwable ex) {
		System.out.println(" UserService is down");
		return new CurrUserDetailResponse("","",null);
	}
	
	@DeleteMapping("/users/{emailId}")
	public BaseResponse deleteUserByEmailId(@PathVariable("emailId") String emailId); 
}
