package com.securepass.authService.models;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.securepass.common_library.models.CurrentUser;

import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Component
@Data
@NoArgsConstructor
public class UserPrinciple implements UserDetails{
	
	private  CurrentUser user ;

	public UserPrinciple(CurrentUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmailId();
	}
	

}
