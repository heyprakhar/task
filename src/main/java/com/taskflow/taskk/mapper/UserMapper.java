package com.taskflow.taskk.mapper;

import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDTO toDto(User user){
        if (user == null){
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roleId(user.getRoleId())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .createdBy(user.getCreatedBy())
                .build();
    }

    public static User toEntity(UserDTO userDTO){
        if (userDTO == null){
            return null;
        }

        return User.builder()
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .roleId(userDTO.getRoleId())
                .build();
    }

    public static void updateEntity(UserDTO userDTO, User user) {

        if (userDTO == null || user == null) {
            return;
        }

        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }

        if (userDTO.getRoleId() != null) {
            user.setRoleId(userDTO.getRoleId());
        }

        if (userDTO.getActive() != null) {
            user.setActive(userDTO.getActive());
        }
    }
}
