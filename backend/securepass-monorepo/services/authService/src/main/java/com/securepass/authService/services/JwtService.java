package com.securepass.authService.services;


import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.KEY;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${common.secretKey}")
	String secretKey;
	

	public String generateToken(String emailId, String role) {
		
		Map<String , Object> claims = new HashMap<String, Object>();
		claims.put("role", role);
		
		
		return Jwts
				.builder()
				.claims()
				.add(claims)
				.subject(emailId)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60*1000*60))
				.and()
				.signWith(getKey())
				.compact();
	}
	
	
public String generateRefreshToken(String emailId, String role) {
		
		Map<String , Object> claims = new HashMap<String, Object>();
		claims.put("role", role);
		
		
		return Jwts
				.builder()
				.claims()
				.add(claims)
				.subject(emailId)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60*1000*60*24*7))
				.and()
				.signWith(getKey())
				.compact();
	}

	
	public SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	 public String extractEmailId(String token) {
	        // extract the username from jwt token
	        return extractClaim(token, Claims::getSubject);
	    }

	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	
		
       final Claims  claims = extractAllClaims(token);
		
        System.out.println("Extracted1");
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
    	 System.out.println("Extracted2");
    	 try {
	        return Jwts.parser()
	                .verifyWith(getKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
    	 
	    } catch (ExpiredJwtException ex) {
	        return ex.getClaims(); // Return claims even if expired
	    }
    }

    public boolean validateToken(String token, String username) {
        final String emailId = extractEmailId(token);
        return (emailId.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}