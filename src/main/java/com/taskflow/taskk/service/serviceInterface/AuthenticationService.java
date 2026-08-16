package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.security.LoginRequestDTO;
import com.taskflow.taskk.dto.security.LoginResponseDTO;

public interface AuthenticationService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
