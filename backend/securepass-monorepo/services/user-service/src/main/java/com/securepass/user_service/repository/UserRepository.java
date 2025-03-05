package com.securepass.user_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.securepass.user_service.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	User findByEmailId(String emailId);
	
	@Query("select new com.securepass.user_service.entities.User(u.userId, u.username, u.fullname, u.emailId, u.password, u.role, u.createdDate, u.updatedDate) from User u"
			+ " where lower(u.fullname) like lower(concat('%',:fullname,'%'))")
	List<User> searchByFullname(String fullname);
	
	
	@Query("select new com.securepass.user_service.entities.User(u.userId, u.username, u.fullname, u.emailId, u.password, u.role, u.createdDate, u.updatedDate) from User u"
			+ " where lower(u.username) like lower(concat('%',:username,'%'))")
	List<User> searchByUsername(String username);
	
	@Query("select new com.securepass.user_service.entities.User(u.userId, u.username, u.fullname, u.emailId, u.password, u.role, u.createdDate, u.updatedDate) from User u"
			+ " where lower(u.emailId) like lower(concat('%',:emailId,'%'))")
	List<User> searchByEmailId(String emailId);
	
	@Query("select new com.securepass.user_service.entities.User(u.userId, u.username, u.fullname, u.emailId, u.password, u.role, u.createdDate, u.updatedDate) from User u"
			+ " where lower(u.role) like lower(concat('%',:role,'%'))")
	List<User> searchByRole(String role);
	
	Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
	
	Page<User> findByEmailIdContainingIgnoreCase(String emailId, Pageable pageable);
	
	Page<User> findByRoleContainingIgnoreCase(String emailId, Pageable pageable);
}
