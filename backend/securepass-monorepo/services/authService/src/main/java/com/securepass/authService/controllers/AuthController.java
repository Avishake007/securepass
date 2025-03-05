package com.securepass.authService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.securepass.authService.services.AuthService;
import com.securepass.common_library.dto.AllUsersResponse;
import com.securepass.common_library.dto.BaseResponse;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.JwtTokenResponse;
import com.securepass.common_library.dto.UserLoginDetails;
import com.securepass.common_library.dto.UserServiceRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtTokenResponse> loginUser(@RequestBody UserLoginDetails userLoginDetails){
		
		return ResponseEntity.status(HttpStatus.OK).body(authService.generateRefreshToken(userLoginDetails));
	} 
	
	@PostMapping("/logout")
	public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String token){
		
		return ResponseEntity.status(HttpStatus.OK).body(authService.logoutUser(token));
	} 
	
	@PostMapping("/register")
	public ResponseEntity<BaseResponse> registerUser(@RequestBody UserServiceRequest userServiceRequest){
		
		return ResponseEntity.status(HttpStatus.OK).body(authService.registerUser(userServiceRequest));
	} 
	
	@GetMapping("/me")
	public ResponseEntity<CurrUserDetailResponse> getLoggedInUserDetail(@RequestHeader("Authorization") String jwtToken){
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(authService.getLoggedInUserDetail(jwtToken.replace("Bearer ", "")));
	} 
	
	@DeleteMapping("/user/{emailId}")
	public ResponseEntity<BaseResponse> deleteUserByEmailId(@PathVariable("emailId") String emailId){
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(authService.deleteUserByEmailId(emailId));
	} 
}
