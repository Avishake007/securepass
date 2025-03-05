package com.securepass.user_service.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.securepass.common_library.dto.AllUsersResponse;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.JwtTokenResponse;
import com.securepass.common_library.dto.NumberOfUsersCountResponse;
import com.securepass.common_library.dto.UserLoginDetails;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.common_library.dto.UsersResponse;
import com.securepass.common_library.dto.BaseResponse;
import com.securepass.common_library.models.CurrentUser;
import com.securepass.user_service.entities.User;
import com.securepass.user_service.exception.UserNotFoundException;
import com.securepass.user_service.repository.UserRepository;
import com.securepass.user_service.service.TokenBlacklistService;
import com.securepass.user_service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	
	private final UserService userService;
	
	
	private final ModelMapper modelMapper ;
	
	 
	

	
	public UserController(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping("/register")
	public ResponseEntity<BaseResponse> registerUser(@Valid @RequestBody  UserServiceRequest userRequest){
		userService.registerUser(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse("0","User created sucessfully"));
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtTokenResponse> loginUser(@Valid @RequestBody  UserLoginDetails userLoginDetails){
		JwtTokenResponse jwtToken = userService.verify(userLoginDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(jwtToken);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<CurrUserDetailResponse> getCurrUserDetail(@PathVariable("userId") String userId){
       
		
		return ResponseEntity.status(HttpStatus.FOUND).body(userService.getCurrUserDetail(userId));
	}
	
	@GetMapping("/fullname")
	public ResponseEntity<AllUsersResponse> searchByFullname(@RequestParam(defaultValue = "") String query){
		List<User> users = userService.searchByFullName(query);
        
		List<CurrentUser> allUsers =  new ArrayList<>();
		
		allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new AllUsersResponse("0","Users successfully fetched", allUsers));
		
	}
	
//	@GetMapping("/username")
//	public ResponseEntity<AllUsersResponse> searchByUsername(@RequestParam(defaultValue = "") String query){
//		List<User> users = userService.searchByUsername(query);
//        
//		List<CurrentUser> allUsers =  new ArrayList<>();
//		
//		allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());
//		
//		
//		return ResponseEntity.status(HttpStatus.OK).body(new AllUsersResponse("0","Users successfully fetched", allUsers));
//		
//	}
	
//	@GetMapping("/emailId")
//	public ResponseEntity<AllUsersResponse> searchByEmailId(@RequestParam(defaultValue = "") String query){
//		List<User> users = userService.searchByEmailId(query);
//        
//		List<CurrentUser> allUsers =  new ArrayList<>();
//		
//		allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());
//		
//		
//		return ResponseEntity.status(HttpStatus.OK).body(new AllUsersResponse("0","Users successfully fetched", allUsers));
//		
//	}
//	
//	@GetMapping("/role")
//	public ResponseEntity<AllUsersResponse> searchByRole(@RequestParam(defaultValue = "") String query){
//		List<User> users = userService.searchByRole(query);
//        
//		List<CurrentUser> allUsers =  new ArrayList<>();
//		
//		allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());
//		
//		
//		return ResponseEntity.status(HttpStatus.OK).body(new AllUsersResponse("0","Users successfully fetched", allUsers));
//		
//	}
	
	@GetMapping("/count")
	public ResponseEntity<NumberOfUsersCountResponse> getNumberOfUsers(){
		long noOfUsers = userService.findNumberOfUsers();
        
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new NumberOfUsersCountResponse("0","Success",noOfUsers));
		
	}
	
	@GetMapping("/except/{emailId}")
	public ResponseEntity<AllUsersResponse> getAllUsersExceptCurr(
			@PathVariable("emailId") String emailId,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "5") int size, 
			@RequestParam(defaultValue = "userId") String sortBy, 
			@RequestParam(defaultValue = "true") boolean ascending){
		
		List<User> users = userService.getAllUsersExceptCurr(emailId,page, size, sortBy, ascending);
		
		List<CurrentUser> allUsers =  new ArrayList<>();
		
		allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());

		
		
		return ResponseEntity.status(HttpStatus.OK).body(new AllUsersResponse("0","Users successfully fetched", allUsers));
	
	}
	
	
	@GetMapping
//	/@PreAuthorize("hasRole(ROLE_ADMIN)")
	public ResponseEntity<AllUsersResponse> getAllUsers(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "5") int size, 
			@RequestParam(defaultValue = "userId") String sortBy, 
			@RequestParam(defaultValue = "true") boolean ascending){
		
		AllUsersResponse allUsers = userService.getAllUsers(page, size, sortBy, ascending);
		
		return ResponseEntity.ok().body(allUsers) ;
	}
	
	@GetMapping("/username")
	public ResponseEntity<UsersResponse> getUsersThroughUsername(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "5") int size, 
			@RequestParam(defaultValue = "userId") String sortBy, 
			@RequestParam(defaultValue = "true") boolean ascending,
			@RequestParam(defaultValue = "") String query
			){
		
		List<User> users = userService.getAllUsersThroughPaginationSortingSearching(page, size, sortBy, ascending, query);
		
		List<CurrentUser> allUsers =  new ArrayList<>();
		
		allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());

		String numberOfUsers = userService.searchByUsername(query).size() +"";
		
		return ResponseEntity.status(HttpStatus.OK).body(new UsersResponse("2","Users successfully fetched", numberOfUsers,allUsers));
	}
	
	@GetMapping("/emailId")
	public ResponseEntity<UsersResponse> getUsersThroughEmailId(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "5") int size, 
			@RequestParam(defaultValue = "userId") String sortBy, 
			@RequestParam(defaultValue = "true") boolean ascending,
			@RequestParam(defaultValue = "") String query
			){
		
		List<User> users = userService.getAllUsersThroughPaginationSortingSearchingEmailId(page, size, sortBy, ascending, query);
		
		List<CurrentUser> allUsers =  new ArrayList<>();
		
		allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());

		String numberOfUsers = userService.searchByEmailId(query).size() +"";
		
		return ResponseEntity.status(HttpStatus.OK).body(new UsersResponse("0","Users successfully fetched", numberOfUsers,allUsers));
	}
	
	
	@GetMapping("/emailId/{emailId}")
	public ResponseEntity<CurrUserDetailResponse> getUserThroughEmailId(@PathVariable("emailId") String emailId){
		
//		if(isAuthorised == false) {
//			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new CurrUserDetailResponse("502","You are not authorised the access this api",null));
//		}
		
		CurrentUser currUser = modelMapper.map(userService.getUserByEmailId(emailId), CurrentUser.class) ;
		
		return ResponseEntity.status(HttpStatus.OK).body(new CurrUserDetailResponse("0","Success",currUser));
	}
	
	@GetMapping("/role")
	public ResponseEntity<UsersResponse> getUsersThroughRole(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "5") int size, 
			@RequestParam(defaultValue = "userId") String sortBy, 
			@RequestParam(defaultValue = "true") boolean ascending,
			@RequestParam(defaultValue = "") String query
			){
		
		List<User> users = userService.getAllUsersThroughPaginationSortingSearchingRole(page, size, sortBy, ascending, query);
		
		List<CurrentUser> allUsers =  new ArrayList<>();
		
		allUsers = users.stream().map(currUser ->modelMapper.map(currUser, CurrentUser.class)).collect(Collectors.toList());

		String numberOfUsers = userService.searchByRole(query).size() +"";
		
		return ResponseEntity.status(HttpStatus.OK).body(new UsersResponse("0","Users successfully fetched",numberOfUsers, allUsers));
	}
	
	@PutMapping("/update")
	public ResponseEntity<BaseResponse> updateUserDetail(@Valid @RequestBody  UserServiceRequest userRequest){
		userService.updateUser(userRequest);
		return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("0","User details updated sucessfully"));
	}
	
//	@DeleteMapping("/{userId}")
//	public ResponseEntity<BaseResponse> deleteUserByUserId(@PathVariable("userId") String userId) {
//		userService.deleteUser(userId);
//		
//		return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("0","User with id "+userId+" is deleted successfully"));
//	}
	
	@DeleteMapping("/{emailId}")
	public ResponseEntity<BaseResponse> deleteUserByEmailId(@PathVariable("emailId") String emailId) {
		
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserByEmailId(emailId));
	}
	
	
	    @PostMapping("/logout")
	    public ResponseEntity<BaseResponse> logout(@RequestHeader("Authorization") String token) {
	    	
	        
	        return ResponseEntity.ok().body(userService.logoutUser(token));
	    }
	

}
