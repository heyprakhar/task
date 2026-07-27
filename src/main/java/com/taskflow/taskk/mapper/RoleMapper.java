package com.taskflow.taskk.mapper;

import com.taskflow.taskk.dto.RoleDTO;
import com.taskflow.taskk.entity.Role;

public class RoleMapper {

    private RoleMapper() {
    }

    public static RoleDTO toDto(Role role) {
        if (role == null) {
            return null;
        }

        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .active(role.isActive())
                .createdBy(role.getCreatedBy())
                .updatedBy(role.getUpdatedBy())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }

    public static Role toEntity(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }

        return Role.builder()
                .name(roleDTO.getName())
                .description(roleDTO.getDescription())
                .active(roleDTO.getActive())
                .build();
    }

    public static void updateEntity(Role role, RoleDTO roleDTO) {
        if (role == null || roleDTO == null) {
            return;
        }

        if(roleDTO.getName() != null){
            role.setName(roleDTO.getName());
        }
        if(roleDTO.getDescription() != null){
            role.setDescription(roleDTO.getDescription());
        }

        if(roleDTO.getActive() != null){
            role.setActive(roleDTO.getActive());
        }

    }
}