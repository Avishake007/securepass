package com.securepass.user_service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.modelmapper.ModelMapper;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.common_library.models.CurrentUser;
import com.securepass.user_service.entities.User;
import com.securepass.user_service.mappers.Mapper;


@ExtendWith(MockitoExtension.class)
public class MapperTest {
	
	@Mock
	BCryptPasswordEncoder passwordEncoder ;
	
	@Mock
	ModelMapper modelMapper;

	
	@InjectMocks
	Mapper mapper;
	
	static SimpleDateFormat formatter;  
    Date date;  
    
    @BeforeAll
    static void beforeAll() {
		
    	formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
    
    
	
	
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		date = new Date();

	}
	

	@Test
	public void shouldMapUserRequestDtoToUser() {
		UserServiceRequest userServiceRequest = new UserServiceRequest("johndoe", "John Doe", "johndoe@gmail.com", passwordEncoder.encode("A12#$abc"), "ADMIN");
		
		User user = mapper.userRequestDtoToUserMapper(userServiceRequest);
		
		assertEquals(userServiceRequest.getUsername(), user.getUsername());
		assertEquals(userServiceRequest.getFullname(), user.getFullname());
		assertEquals(userServiceRequest.getEmailId(), user.getEmailId());
		assertEquals(userServiceRequest.getPassword(), user.getPassword());
		assertEquals(userServiceRequest.getRole(), user.getRole());
		
		assertNotNull(user.getUpdatedDate());
		assertNotNull(user.getCreatedDate());
		
		
	}
	
	@Test
	public void shoulduserToCurrUserDetailResponseMapper() {
		User user = new User(
				"1234",
				"johndoe", 
				"John Doe", 
				"johndoe@gmail.com", 
				passwordEncoder.encode("A12#$abc"), 
				"ADMIN", 
				formatter.format(date), 
				formatter.format(date));
		
		CurrentUser currentUser = new CurrentUser("1234",
				"johndoe", 
				"John Doe", 
				"johndoe@gmail.com", 
				passwordEncoder.encode("A12#$abc"), 
				"ADMIN", 
				date, 
				date);
		
//		CurrUserDetailResponse currUserDetailResponse = new CurrUserDetailResponse(
//				"0",
//				"User with id : "+currentUser.getUserId()+" is successfully fetched", 
//				currentUser);

		when(modelMapper.map(user,CurrentUser.class)).thenReturn(currentUser);
		CurrentUser currUser = mapper.userToCurrUserMapper(user);
		//when(mapper.userToCurrUserDetailResponseMapper(user)).thenReturn(currUserDetailResponse);
		
		assertEquals(currentUser, currUser);
		
	}
}
