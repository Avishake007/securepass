package com.securepass.user_service.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.securepass.common_library.dto.AllUsersResponse;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.common_library.models.CurrentUser;
import com.securepass.user_service.entities.User;
import com.securepass.user_service.exception.UserNotFoundException;
import com.securepass.user_service.mappers.Mapper;
import com.securepass.user_service.repository.UserRepository;
import com.securepass.user_service.service.JwtService;
import com.securepass.user_service.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	BCryptPasswordEncoder passwordEncoder ;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	AuthenticationManager authManager;
	
	
	
	@Mock
	JwtService jwtService;
	
	@Mock
	Mapper mapper;
	
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

	}
	
	@Test
	public void shouldNotThrowExceptionInRegisterUser() {
		
		UserServiceRequest userServiceRequest = new UserServiceRequest(
				"johndoe", 
				"John Doe", 
				"johndoe@gmail.com", 
				passwordEncoder.encode("A12#$abc"), 
				"ADMIN");
		
		assertDoesNotThrow(() -> userServiceImpl.registerUser(userServiceRequest));
		
	}
	
	@Test
	public void shouldGetCurrUserDetail() {
		String userId = "1234";
		
		User user = new User(
				"1234",
				"johndoe", 
				"John Doe", 
				"johndoe@gmail.com", 
				passwordEncoder.encode("A12#$abc"), 
				"ADMIN", 
				formatter.format(date), 
				formatter.format(date));
		
		CurrentUser currentUser = new CurrentUser(
				"1234",
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
		
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		
		when(mapper.userToCurrUserMapper(user)).thenReturn(currentUser);
		
		CurrUserDetailResponse currRes = userServiceImpl.getCurrUserDetail(userId);
		
		assertEquals("0", currRes.getResponseCode());
		assertEquals("User with id : "+currentUser.getUserId()+" is successfully fetched", currRes.getReponseStatus());
		assertNotNull(currRes.getUser());
		assertEquals(currentUser,currRes.getUser());
		assertDoesNotThrow(() ->userServiceImpl.getCurrUserDetail(userId));
	}
	
	@Test
	public void shouldThrowUserNotFoundExceptionInGetCurrUserDetail() {
		String userId = "1234";
		
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		
		UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userServiceImpl.getCurrUserDetail(userId));
		
		assertEquals("User with id : "+userId+" is not present",ex.getMessage());
		
	}
//	
//	@Test
//	public void shouldGetAllUsersUsingPaginationSorting() {
//		Sort sort = Sort.by("emailId").ascending();
//		Pageable pageable = PageRequest.of(0, 5,sort);
//		
//		List<User> users = List.of(new User(
//				"1234",
//				"johndoe", 
//				"John Doe", 
//				"aohndoe@gmail.com", 
//				passwordEncoder.encode("A12#$abc"), 
//				"ADMIN", 
//				formatter.format(date), 
//				formatter.format(date)),
//				new User(
//						"1234",
//						"johndoe", 
//						"John Doe", 
//						"bohndoe@gmail.com", 
//						passwordEncoder.encode("A12#$abc"), 
//						"ADMIN", 
//						formatter.format(date), 
//						formatter.format(date)),
//				new User(
//						"1234",
//						"johndoe", 
//						"John Doe", 
//						"johndoe@gmail.com", 
//						passwordEncoder.encode("A12#$abc"), 
//						"ADMIN", 
//						formatter.format(date), 
//						formatter.format(date)));
//		
//		List<CurrentUser> currUsers = List.of(new CurrentUser(
//				"1234",
//				"johndoe", 
//				"John Doe", 
//				"aohndoe@gmail.com", 
//				passwordEncoder.encode("A12#$abc"), 
//				"ADMIN", 
//				date, 
//				date),
//				new CurrentUser(
//						"1234",
//						"johndoe", 
//						"John Doe", 
//						"bohndoe@gmail.com", 
//						passwordEncoder.encode("A12#$abc"), 
//						"ADMIN", 
//						date, 
//						date),
//				new CurrentUser(
//						"1234",
//						"johndoe", 
//						"John Doe", 
//						"johndoe@gmail.com", 
//						passwordEncoder.encode("A12#$abc"), 
//						"ADMIN", 
//						date, 
//						date));
//		
//		when(userRepository.findAll(pageable).getContent()).thenReturn(users);
//		 when(mapper.usersToCurrUsersMapper(users)).thenReturn(currUsers);
//		
//		AllUsersResponse usersLi = new AllUsersResponse("0", "Users successfully fetched", currUsers);
//		
//		assertEquals(users, usersLi);
//	}
	
	@Test
	public void shouldThrowUserNotFoundExceptionWhenUserIdIsNull() {
		assertThrows(UserNotFoundException.class, () ->userServiceImpl.deleteUser(null));
		
	}
	
	@Test
	public void shouldGetUserByEmailId() {
		User user = new User(
				"1234",
				"johndoe", 
				"John Doe", 
				"johndoe@gmail.com", 
				passwordEncoder.encode("A12#$abc"), 
				"ADMIN", 
				formatter.format(date), 
				formatter.format(date));
		when(userRepository.findByEmailId("johndoe@gmail.com")).thenReturn(user);
		
		assertEquals(user,userServiceImpl.getUserByEmailId("johndoe@gmail.com"));
		assertDoesNotThrow(() ->userServiceImpl.getUserByEmailId("johndoe@gmail.com"));
		
	}
}
