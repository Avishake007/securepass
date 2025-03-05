package com.securepass.authService.services;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.securepass.authService.advice.exceptions.UserEmailNotFoundException;
import com.securepass.authService.external.services.UserService;
import com.securepass.authService.models.UserPrinciple;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.models.CurrentUser;

import io.github.resilience4j.retry.annotation.Retry;




@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	
	 @Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UserEmailNotFoundException {
		System.out.println("Hiop");
		CurrentUser user = userService.getUserThroughEmailId(emailId).getUser();
		
		if(user == null) {
			throw new UserEmailNotFoundException("Invalid email");
		}
		
		return new  UserPrinciple(user);
	}
	

}
