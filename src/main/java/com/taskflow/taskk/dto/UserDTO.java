package com.taskflow.taskk.dto;

import com.taskflow.taskk.entity.Role;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Long roleId;
    private Boolean active;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
