package com.securepass.common_library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceRequest {
	
	@NotBlank(message = "Please enter a username")
	@Size(min = 2, message="Length of the username must be atleast 2 characters")
	private String username;
	
	@NotBlank(message = "Please enter your fullname")
	@Size(min = 2, message="Length of the username must be atleast 2 characters")
	private String fullname;
	
	@NotBlank(message = "Please enter your emailId")
	@Email(message = "Please enter a valid email address")
	private String emailId;
	
	@NotBlank(message = "Please enter your password")
	@Pattern(
	        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])\\S{8,}$",
	        message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character."
	    )
	private String password;
	
	@NotBlank(message = "Please enter your role")
	@NotNull(message = "Role cannot be null")
	@Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be either USER or ADMIN")
	private String role;
	

}
