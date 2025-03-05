package com.securepass.authService.configurations;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientInterceptors implements RequestInterceptor{
	
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

	@Override
	public void apply(RequestTemplate template) {
		  
	            String token = getJwtTokenFromRequest();
	            if (token != null) {
	            	template.header(AUTHORIZATION_HEADER, token);
	            }
	        
		
	}
	
	private String getJwtTokenFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            System.out.println("Auth "+request.getHeader(AUTHORIZATION_HEADER));
            return request.getHeader(AUTHORIZATION_HEADER);
        }
        return null;
    }
	
}
