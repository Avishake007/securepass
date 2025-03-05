package com.securepass.authService.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.securepass.common_library.dto.AllUsersResponse;
import com.securepass.common_library.dto.BaseResponse;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.JwtTokenResponse;
import com.securepass.common_library.dto.UserLoginDetails;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.common_library.models.CurrentUser;

@Service
public interface AuthService {
	
	public BaseResponse registerUser(UserServiceRequest userServiceRequest);
	
	//public JwtTokenResponse loginUser(UserLoginDetails userLoginDetails);
	
	public String logoutUser(String token);
	
	public JwtTokenResponse generateAccessToken(UserLoginDetails userLoginRequestDto);
	
	public CurrUserDetailResponse getLoggedInUserDetail(String token);
	
	public BaseResponse deleteUserByEmailId(String emailId);
	
	public JwtTokenResponse generateRefreshToken(UserLoginDetails userLoginRequestDto);
	
	
	
	
	
}
