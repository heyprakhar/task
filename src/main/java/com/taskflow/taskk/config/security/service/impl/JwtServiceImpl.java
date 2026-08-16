package com.taskflow.taskk.config.security.service.impl;


import com.taskflow.taskk.config.security.CustomUserDetails;
import com.taskflow.taskk.config.security.JwtProperties;
import com.taskflow.taskk.config.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey(){

        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

    }

    @Override
    public String generateToken(CustomUserDetails customUserDetails){

        Date now = new Date();
        Date expiration = new Date(now.getTime()+jwtProperties.getExpiration());

        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token, CustomUserDetails customUserDetails) {
        String username = extractUsername(token);

        return username.equals(customUserDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
