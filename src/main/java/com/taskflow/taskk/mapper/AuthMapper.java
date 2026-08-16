package com.taskflow.taskk.mapper;

import com.taskflow.taskk.config.security.CustomUserDetails;
import com.taskflow.taskk.dto.security.LoginResponseDTO;

public class AuthMapper {

    private AuthMapper(){}

    public static LoginResponseDTO toResponse(CustomUserDetails userDetails, String token) {

        if (userDetails == null) {
            return null;
        }

        return LoginResponseDTO.builder()
                .userId(userDetails.getUserId())
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .roleId(userDetails.getRoleId())
                .token(token)
                .build();

    }
}
