package com.securepass.common_library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NumberOfUsersCountResponse extends BaseResponse{
	

	long count;
	
	public NumberOfUsersCountResponse(String responseCode, String responseDescription, long noOfUsers) {
		super(responseCode,responseDescription);
		this.count = noOfUsers;
	}

	
	
}
