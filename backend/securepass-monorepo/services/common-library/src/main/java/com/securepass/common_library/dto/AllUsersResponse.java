package com.securepass.common_library.dto;
import java.util.*;

import com.securepass.common_library.models.CurrentUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllUsersResponse extends BaseResponse{
	List<CurrentUser> users;

	public AllUsersResponse(String responseCode, String reponseStatus, List<CurrentUser> users) {
		super(responseCode, reponseStatus);
		this.users = users;
	}
	
	
	
}
