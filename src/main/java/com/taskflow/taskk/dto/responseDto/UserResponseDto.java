package com.taskflow.taskk.dto.responseDto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String roleName;
    private boolean isActive;

}