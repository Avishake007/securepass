package com.securepass.common_library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenResponse extends BaseResponse{
	String emailId;
	String jwtToken;
	String expiryDate;
	String role;
	
	public JwtTokenResponse(String responseCode, String responseDescription, String emailId, String jwtToken, String expiryDate, String role) {
		super(responseCode, responseDescription);
		this.emailId = emailId;
		this.jwtToken = jwtToken;
		this.expiryDate = expiryDate;
		this.role = role;
		
	}
}
