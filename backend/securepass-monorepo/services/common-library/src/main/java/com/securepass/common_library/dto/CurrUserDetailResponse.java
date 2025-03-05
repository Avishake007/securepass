package com.securepass.common_library.dto;


import com.securepass.common_library.models.CurrentUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrUserDetailResponse extends BaseResponse{
	private CurrentUser user;
	
	public CurrUserDetailResponse(String responseCode, String responseDescription, CurrentUser user) {
		super(responseCode, responseDescription);
		this.user = user;
	}
}
