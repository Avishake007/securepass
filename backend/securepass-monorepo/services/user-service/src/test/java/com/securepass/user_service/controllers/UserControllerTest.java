package com.securepass.user_service.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.models.CurrentUser;
import com.securepass.user_service.controllers.UserController;
import com.securepass.user_service.service.UserService;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	 
	 @InjectMocks 
	 UserController userController;
	 
	 @Mock
	 UserService userService;
	 
	 @Mock
	 ModelMapper modelMapper ;
	 
	 
	
	static SimpleDateFormat formatter;  
    Date date;  
    
    @BeforeAll
    static void beforeAll() {
		
    	formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	@BeforeEach
	 void setup() {
		MockitoAnnotations.openMocks(this);
		date = new Date();
		System.out.println("Testing started");
	
	}
	 
	@Test
	 public void testGetCurrUserDetail_Success() throws Exception {
		 String userId = "1234";
		 
		 CurrentUser currentUser = new CurrentUser(
					"1234",
					"johndoe", 
					"John Doe", 
					"johndoe@gmail.com", 
					"A12#$abc", 
					"ADMIN", 
					date, 
					date);
		 
		CurrUserDetailResponse currUserDetailResponse = new CurrUserDetailResponse(
			"0", 
			"User with id : "+userId+" is successfully fetched", 
			currentUser);
		 
		 when(userService.getCurrUserDetail(userId)).thenReturn(currUserDetailResponse);
		 
		ResponseEntity<CurrUserDetailResponse> currRes =  userController.getCurrUserDetail(userId);
		
		assertEquals(HttpStatus.FOUND, currRes.getStatusCode());
		
		 
//		 mockMvc.perform(get("/users/{userId}",userId)
//		  	.contentType(MediaType.APPLICATION_JSON))
//			.andExpect(status().isFound())
//			 .andExpect(jsonPath("responseCode").value("0"));
	 }
	 
	
	@AfterEach
	private void teardown() {
		date = null;
		System.gc();
		
		System.out.println("Testing ended");

	}
	 
}
