package com.securepass.user_service.service;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.securepass.user_service.entities.User;
import com.securepass.user_service.exception.UserEmailNotFoundException;
import com.securepass.user_service.models.UserPrinciple;
import com.securepass.user_service.repository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	
	 @Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UserEmailNotFoundException {
		User user = userRepository.findByEmailId(emailId);
		
		if(user == null) {
			throw new UserEmailNotFoundException("Invalid email");
		}
		
		return new  UserPrinciple(user);
	}

}
