package com.securepass.authService.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.securepass.authService.advice.exceptions.UserNotAuthorisedException;
import com.securepass.authService.entity.RefreshToken;
import com.securepass.authService.external.services.UserService;
import com.securepass.authService.repository.RefreshTokenRepository;
import com.securepass.common_library.dto.BaseResponse;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.JwtTokenResponse;
import com.securepass.common_library.dto.UserLoginDetails;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.common_library.models.CurrentUser;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	RestTemplate restTemplate;
	
//	@Autowired
//	ModelMapper modelMapper;
	
	@Autowired
	UserService userService;
	

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	private TokenBlacklistService tokenBlacklistService;
	
	 @Autowired
	private RefreshTokenRepository refreshTokenRepository;

//	@Override
//	public AllUsersResponse getAllUsers() {
//		//ResponseEntity<AllUsersResponse> users = restTemplate.getForEntity("http:USERSERVICE/users", AllUsersResponse.class);
//		
//		
//		//List<CurrentUser> allUsers =  new ArrayList<>();
//		
//		//allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());
//
//		return users.getBody();
//	}

//	@Override
//	public JwtTokenResponse loginUser(UserLoginDetails userLoginDetails) {
//		//ResponseEntity<JwtTokenResponse> jwtTokenResponse = restTemplate.postForEntity("http://localhost:8082/users/login", userLoginDetails, JwtTokenResponse.class);
//		return userService.loginUser(userLoginDetails);
//	}

	@Override
	@CircuitBreaker(name = "registerUserBreaker", fallbackMethod = "registerUserFallback")
	public BaseResponse registerUser(UserServiceRequest userServiceRequest) {
	
		return userService.registerUser(userServiceRequest);
	}
	
	public BaseResponse registerUserFallback(UserServiceRequest userServiceRequest, Exception e) {
		return new BaseResponse("0","User service is currently down");
	}
	
	@Override
	public String logoutUser(String token) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }

        tokenBlacklistService.blacklistToken(token);
        return "Logged out successfully";
	}
	
	@Override
	@Transactional
	public JwtTokenResponse generateAccessToken(UserLoginDetails userLoginRequestDto) {
		
		Authentication authentication = null;
		
		System.out.println("User Details : "+userLoginRequestDto.getEmailId() +" "+ userLoginRequestDto.getPassword());
		try {
		 authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmailId(), userLoginRequestDto.getPassword()));
		 System.out.println("authentication : "+authentication);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new UserNotAuthorisedException("Bad Credentials");
		}
		
		
		
		
		try {
		if(authentication.isAuthenticated()) {
			CurrentUser user = userService.getUserThroughEmailId( userLoginRequestDto.getEmailId()).getUser();
			
			String jwtToken = jwtService.generateToken(userLoginRequestDto.getEmailId(), user.getRole());
			System.out.println("Generated " +jwtToken);
			DecodedJWT decodedJWT = JWT.decode(jwtToken);
			String role = decodedJWT.getClaim("role").asString();
			
			return new JwtTokenResponse("0","Success",userLoginRequestDto.getEmailId(),jwtToken,decodedJWT.getExpiresAt()+"",role);
		}
		}
		catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return null;
		
		
	}

	int retry = 1;
	@Override
	//@CircuitBreaker(name = "getLoggedInUserDetailBreaker", fallbackMethod = "getLoggedInUserDetailFallback")
	@Retry(name ="getLoggedInUserDetailRetry", fallbackMethod = "getLoggedInUserDetailFallback")
	public CurrUserDetailResponse getLoggedInUserDetail(String token) {
		System.out.println(retry);
		String emailId = jwtService.extractEmailId(token);
		System.out.println(emailId);
		
		return userService.getUserThroughEmailId( emailId);
	}
	
	public CurrUserDetailResponse getLoggedInUserDetailFallback(String token, Exception ex) {
		System.out.println("Hi");
		return new CurrUserDetailResponse("0","User Service is currently down",null);
	}

	@Override
	public BaseResponse deleteUserByEmailId(String emailId) {
		System.out.println("delete");
		return userService.deleteUserByEmailId(emailId);
	}

	@Override
	@Transactional
	public JwtTokenResponse generateRefreshToken( UserLoginDetails userLoginRequestDto) {
	Authentication authentication = null;
		
		System.out.println("User Details : "+userLoginRequestDto.getEmailId() +" "+ userLoginRequestDto.getPassword());
		try {
		 authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmailId(), userLoginRequestDto.getPassword()));
		 System.out.println("authentication : "+authentication);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new UserNotAuthorisedException("Bad Credentials");
		}
		
		
		
		
		try {
		if(authentication.isAuthenticated()) {
			CurrentUser user = userService.getUserThroughEmailId( userLoginRequestDto.getEmailId()).getUser();
			
			String refreshToken = jwtService.generateRefreshToken(userLoginRequestDto.getEmailId(), user.getRole());
			System.out.println("Generated Refresh Token : " +refreshToken);
			
			DecodedJWT decodedRefreshToken = JWT.decode(refreshToken);
			String role = decodedRefreshToken.getClaim("role").asString();
			
			RefreshToken refereshToken = new RefreshToken(null, refreshToken, userLoginRequestDto.getEmailId(), decodedRefreshToken.getExpiresAt());
			
			refreshTokenRepository.deleteByUsername(userLoginRequestDto.getEmailId());
			refreshTokenRepository.save(refereshToken);
			
			return generateAccessToken(userLoginRequestDto);
		}
		}
		catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return null;	}
	
	
	
}
