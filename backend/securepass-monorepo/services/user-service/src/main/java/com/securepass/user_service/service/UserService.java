package com.securepass.user_service.service;

import java.util.List;

import com.securepass.common_library.dto.AllUsersResponse;
import com.securepass.common_library.dto.BaseResponse;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.JwtTokenResponse;
import com.securepass.common_library.dto.UserLoginDetails;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.user_service.entities.User;

public interface UserService {
	
	void registerUser(UserServiceRequest userServiceRequest);
	
	JwtTokenResponse verify(UserLoginDetails userLoginRequestDto);
	
	CurrUserDetailResponse getCurrUserDetail(String userId);
	
	List<User> getAllUsersExceptCurr(String emailId,int page, int size, String sortBy, boolean ascending);
	
	List<User> getAllUsersThroughPaginationSortingSearching(int page, int size, String sortBy, boolean ascending, String query);
	
	List<User> getAllUsersThroughPaginationSortingSearchingEmailId(int page, int size, String sortBy, boolean ascending, String query);

	List<User> getAllUsersThroughPaginationSortingSearchingRole(int page, int size, String sortBy, boolean ascending, String query);

	List<User> searchByFullName(String query);
	
	List<User> searchByUsername(String query);
	
	List<User> searchByEmailId(String query);
	
	List<User> searchByRole(String query);

	
	User getUserByEmailId(String emailId);
	
	long findNumberOfUsers();
	
	AllUsersResponse getAllUsers(int page, int size, String sortBy, boolean ascending);
	
	void updateUser(UserServiceRequest userServiceRequest);
	
	BaseResponse deleteUser(String userId);
	
	BaseResponse deleteUserByEmailId(String emailId);
	
	BaseResponse logoutUser(String token);
}
