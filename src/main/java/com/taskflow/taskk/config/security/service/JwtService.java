package com.taskflow.taskk.config.security.service;

import com.taskflow.taskk.config.security.CustomUserDetails;

public interface JwtService {

    String generateToken(CustomUserDetails customUserDetails);

    String extractUsername(String token);

    boolean isTokenValid(String token, CustomUserDetails customUserDetails);
}