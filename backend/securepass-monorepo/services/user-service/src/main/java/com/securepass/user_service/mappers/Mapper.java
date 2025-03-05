package com.securepass.user_service.mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.common_library.models.CurrentUser;
import com.securepass.user_service.entities.User;

@Component
public class Mapper {
	
	@Autowired
	 BCryptPasswordEncoder passwordEncoder ;
	
	@Autowired
	ModelMapper modelMapper ;

	
	public  User userRequestDtoToUserMapper(UserServiceRequest userServiceRequest) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date();  
        
        User user = new User();
        
        
		
        user.setUsername(userServiceRequest.getUsername());
		user.setFullname(userServiceRequest.getFullname());
		user.setEmailId(userServiceRequest.getEmailId());
		user.setPassword(passwordEncoder.encode(userServiceRequest.getPassword()));
		user.setRole(userServiceRequest.getRole());
		user.setCreatedDate(formatter.format(date));
		user.setUpdatedDate(formatter.format(date));
		
		return user;
	}
	
	public CurrentUser userToCurrUserMapper(User user) {
		 CurrentUser currUser = modelMapper.map(user, CurrentUser.class);
		 
		 return currUser;
	}
	
	public List<CurrentUser> usersToCurrUsersMapper(List<User> users) {
		List<CurrentUser> allUsers = users.stream().map(currUser ->userToCurrUserMapper(currUser)).collect(Collectors.toList());
		
		return allUsers;
	}
}
