package com.securepass.common_library.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {
	
	private String userId;	
	private String username;
	private String fullname;
	private String emailId;
	private String password;
	private String role;
	private Date createdDate;
	private Date updatedDate;
	
}
