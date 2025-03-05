package com.securepass.authService.filters;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securepass.authService.advice.exceptions.CustomFilterException;
import com.securepass.authService.advice.exceptions.UserNotAuthorisedException;
import com.securepass.authService.repository.RefreshTokenRepository;
import com.securepass.authService.services.JwtService;
import com.securepass.authService.services.TokenBlacklistService;
import com.securepass.authService.services.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	ApplicationContext context;
	
	 @Autowired
	  private TokenBlacklistService tokenBlacklistService;
	 
	 @Autowired
	 private RefreshTokenRepository refreshTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, UserNotAuthorisedException {
		
		
		 String authHeader = request.getHeader("Authorization");
        String token = null;
        String emailId = null;
        UserDetails userDetails = null;
        
        try {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                return;
            }
            System.out.println("Expired EmailId "+emailId);
            emailId = jwtService.extractEmailId(token);
            System.out.println("Expired EmailId "+emailId);
        }
        
      
        
        System.out.println("EmailId  "+emailId + "token "+authHeader);
        
        if (emailId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(emailId);
            try {
            	  String refreshToken = refreshTokenRepository.findByUsername(emailId).get().getToken();
            	  if(!jwtService.validateToken(refreshToken, emailId)) {
            		  throw new ExpiredJwtException(null, null, emailId);
            	  }
            }
            catch(ExpiredJwtException e) {
            	handleRefreshTokenException(response, new CustomFilterException("Refresh Token is expired Please login again", HttpServletResponse.SC_FORBIDDEN));
            }
            if (jwtService.validateToken(token, emailId)) {
            	userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(emailId);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            else {
            	throw new ExpiredJwtException(null, null, emailId);
            }
        }
        
       

        System.out.println("pp");

        filterChain.doFilter(request, response);
        }
        catch(ExpiredJwtException exJwt) {
        	
        	String newToken = jwtService.generateToken(emailId, "ADMIN");
        	handleAccessTOkenException(response, new CustomFilterException(newToken, HttpServletResponse.SC_FORBIDDEN));
        }
        catch(Exception e) {
        	e.printStackTrace();
        	handleAccessTOkenException(response, new CustomFilterException("Something went wrong", HttpServletResponse.SC_FORBIDDEN));
       }
		
	}
	
	 private void handleAccessTOkenException(HttpServletResponse response, CustomFilterException ex) throws IOException {
	        response.setStatus(ex.getStatusCode());
	        response.setContentType("application/json");
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("error", ex.getMessage());
	        errorResponse.put("status", ex.getStatusCode());

	        // Convert to JSON and write response
	        ServletOutputStream out = response.getOutputStream();
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.writeValue(out, errorResponse);
	        out.flush();
	    }
	 
	 private void handleRefreshTokenException(HttpServletResponse response, CustomFilterException ex) throws IOException {
	        response.setStatus(ex.getStatusCode());
	        response.setContentType("application/json");
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("responseCode", ex.getStatusCode());
	        errorResponse.put("responseStatus", ex.getMessage());

	        // Convert to JSON and write response
	        ServletOutputStream out = response.getOutputStream();
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.writeValue(out, errorResponse);
	        out.flush();
	    }


}
