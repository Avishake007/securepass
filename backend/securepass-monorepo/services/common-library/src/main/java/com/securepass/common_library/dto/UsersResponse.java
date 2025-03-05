package com.securepass.common_library.dto;

import java.util.List;

import com.securepass.common_library.models.CurrentUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse extends BaseResponse{
	String numberOfUsers;
	List<CurrentUser> users;

	public UsersResponse(String responseCode, String reponseStatus, String numberOfUsers,List<CurrentUser> users) {
		super(responseCode, reponseStatus);
		this.numberOfUsers = numberOfUsers;
		this.users = users;
	}
}
