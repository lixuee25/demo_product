package com.products.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

	String extractUserName(String jwt);

	String createToken(UserDetails userDetails);
    
    boolean isTokenValid(String token,UserDetails userDetails);

	String createRefeshToken(Map<String, Object> extraClaims, UserDetails userDetails);
    
}