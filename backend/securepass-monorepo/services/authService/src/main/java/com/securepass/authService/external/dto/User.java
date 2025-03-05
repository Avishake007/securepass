package com.securepass.authService.external.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private String userId;
	
	private String username;
	
	private String fullname;
	
	private String emailId;
	
	private String password;
	
	private String role;
	
	private String createdDate;
	private String updatedDate;
}
