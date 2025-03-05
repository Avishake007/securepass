package com.securepass.common_library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserLoginDetails {
	
	@NotBlank(message = "EmailId cannot be blank")
	String emailId;
	
	@NotBlank(message = "Password cannot be blank")
	String password;
}
