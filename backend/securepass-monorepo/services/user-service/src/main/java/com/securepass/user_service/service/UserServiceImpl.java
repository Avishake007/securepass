package com.securepass.user_service.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.securepass.common_library.dto.AllUsersResponse;
import com.securepass.common_library.dto.BaseResponse;
import com.securepass.common_library.dto.CurrUserDetailResponse;
import com.securepass.common_library.dto.JwtTokenResponse;
import com.securepass.common_library.dto.UserLoginDetails;
import com.securepass.common_library.dto.UserServiceRequest;
import com.securepass.common_library.models.CurrentUser;
import com.securepass.user_service.entities.User;
import com.securepass.user_service.exception.DuplicateEmailIdException;
import com.securepass.user_service.exception.UserEmailNotFoundException;
import com.securepass.user_service.exception.UserNotAuthorisedException;
import com.securepass.user_service.exception.UserNotFoundException;
import com.securepass.user_service.mappers.Mapper;
import com.securepass.user_service.repository.UserRepository;

import jakarta.validation.Valid;



@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	Mapper mapper;
	
	@Autowired
	TokenBlacklistService tokenBlacklistService;

	
	
	
	
	@Transactional
	@Override
	public void registerUser(@Valid UserServiceRequest userServiceRequest) {
		
		if(userRepository.findByEmailId(userServiceRequest.getEmailId()) != null)
			throw new DuplicateEmailIdException("Duplicate Email Id. Please use a different one");
		
		
		
		userRepository.save(mapper.userRequestDtoToUserMapper(userServiceRequest));
		
	}


	@Override
	@Transactional(readOnly = true)
	public CurrUserDetailResponse getCurrUserDetail(String userId) {
		
		
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id : "+userId+" is not present"));
		
		CurrentUser currUser = mapper.userToCurrUserMapper(user);
		
		return new CurrUserDetailResponse("0" ,"User with id : "+currUser.getUserId()+" is successfully fetched",currUser);
	}


	
	@Override
	@Transactional(readOnly = true)
	public 	AllUsersResponse getAllUsers(int page, int size, String sortBy, boolean ascending)
 {
		
		
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size,sort);
		
		List<User> users = userRepository.findAll(pageable).getContent();
		
		List<CurrentUser> allUsers =  mapper.usersToCurrUsersMapper(users);
		
		
		return new AllUsersResponse("0","Users successfully fetched", allUsers);
	}
	
	@Override
	@Transactional(readOnly = true)
	public 	List<User> getAllUsersThroughPaginationSortingSearching(int page, int size, String sortBy, boolean ascending, String query)
 {
		
		
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size,sort);
		
		
		return userRepository.findByUsernameContainingIgnoreCase(query, pageable).getContent();
	}


	@Override
	public BaseResponse deleteUser(String userId) {
		getCurrUserDetail(userId);
		
		userRepository.deleteById(userId);
		
		
		return new BaseResponse("0","User with id "+userId+" is deleted successfully");
	}
	
	@Override
	public BaseResponse deleteUserByEmailId(String emailId) {
		System.out.println("Response");
		User user = getUserByEmailId(emailId);
		
		userRepository.delete(user);
		
		return new BaseResponse("0","User with id "+user.getUserId()+" is deleted successfully");
		
	}


	@Transactional
	@Override
	public void updateUser(UserServiceRequest userServiceRequest) {
		
		User user = userRepository.findById(userServiceRequest.getEmailId()).orElseThrow(() -> new UserNotFoundException("User with id : "+userServiceRequest.getEmailId()+"is not present"));
		
		user.setFullname(userServiceRequest.getFullname());
		user.setPassword(userServiceRequest.getPassword());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date();  
        
        user.setUpdatedDate(formatter.format(date));
		
	}


	@Override
	public User getUserByEmailId(String emailId) throws UserEmailNotFoundException{
		
		User user = userRepository.findByEmailId(emailId);
		
		if(user == null) {
			throw new UserEmailNotFoundException("Invalid email");
		}
		
		return user;
	}

	@Override
	public JwtTokenResponse verify(UserLoginDetails userLoginRequestDto) throws UserNotAuthorisedException{
		
		Authentication authentication = null;
		
		System.out.println("User Details : "+userLoginRequestDto.getEmailId() +" "+ userLoginRequestDto.getPassword());
		try {
		 authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmailId(), userLoginRequestDto.getPassword()));
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new UserNotAuthorisedException("Bad Credentials");
		}
		
		System.out.println("authentication : "+authentication);
		
			
		if(authentication.isAuthenticated()) {
			User user = getUserByEmailId(userLoginRequestDto.getEmailId());
			
			String jwtToken = jwtService.generateToken(userLoginRequestDto.getEmailId(), user.getRole());
			DecodedJWT decodedJWT = JWT.decode(jwtToken);
			String role = decodedJWT.getClaim("role").asString();
			return new JwtTokenResponse("0","Success",userLoginRequestDto.getEmailId(),jwtToken,decodedJWT.getExpiresAt()+"",role);
		}
		throw new UserNotAuthorisedException("Bad Credentials");
		
	}


	@Override
	public List<User> searchByFullName(String query) {
		
		return userRepository.searchByFullname(query);
	}


	@Override
	public long findNumberOfUsers() {
		
		return userRepository.findAll().stream().count();
	}


	@Override
	public List<User> searchByUsername(String query) {

		return userRepository.searchByUsername(query);

	}


	@Override
	public List<User> getAllUsersExceptCurr(String emailId,int page, int size, String sortBy, boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size,sort);
		return userRepository.findAll(pageable).stream().filter(user -> !user.getEmailId().equalsIgnoreCase(emailId)).toList();
	}


	@Override
	public List<User> searchByEmailId(String query) {
		return userRepository.searchByEmailId(query);
	}


	@Override
	public List<User> searchByRole(String query) {
		return userRepository.searchByRole(query);
	}


	@Override
	public List<User> getAllUsersThroughPaginationSortingSearchingEmailId(int page, int size, String sortBy,
			boolean ascending, String query) {
		
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size,sort);
		
		
		return userRepository.findByEmailIdContainingIgnoreCase(query, pageable).getContent();
	}


	@Override
	public List<User> getAllUsersThroughPaginationSortingSearchingRole(int page, int size, String sortBy,
			boolean ascending, String query) {
	Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size,sort);
		
		
		return userRepository.findByRoleContainingIgnoreCase(query, pageable).getContent();
	}


	@Override
	public BaseResponse logoutUser(String token) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }

        tokenBlacklistService.blacklistToken(token);
		return new BaseResponse("0" ,"User is logout successfully");
	}

}

