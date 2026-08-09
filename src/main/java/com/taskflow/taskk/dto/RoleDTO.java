package com.taskflow.taskk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean active;

    private Long createdBy;

    private Long updatedBy;

    private Long deactivatedBy;

    private Long activatedBy;

    private LocalDateTime reactivatedTime;

    private LocalDateTime deactivatedTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}