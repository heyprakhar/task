package com.taskflow.taskk.dto.responseDto;

import lombok.Data;
import java.util.UUID;

@Data
public class UserResponseDto {

    private UUID id;
    private String name;
    private String email;
    private String roleName;
    private boolean isActive;

}