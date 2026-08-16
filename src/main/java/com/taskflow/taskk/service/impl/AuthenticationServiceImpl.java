package com.taskflow.taskk.service.impl;


import com.taskflow.taskk.config.security.CustomUserDetails;
import com.taskflow.taskk.config.security.service.JwtService;
import com.taskflow.taskk.dto.security.LoginRequestDTO;
import com.taskflow.taskk.dto.security.LoginResponseDTO;
import com.taskflow.taskk.exceptions.BusinessException;
import com.taskflow.taskk.exceptions.ErrorCode;
import com.taskflow.taskk.mapper.AuthMapper;
import com.taskflow.taskk.service.serviceInterface.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(customUserDetails);
            return AuthMapper.toResponse(customUserDetails,token);

        }
        catch (BadCredentialsException e) {
            throw new BusinessException(ErrorCode.INVALID_ACCESS,"Invalid username or password");
        }
    }
}
