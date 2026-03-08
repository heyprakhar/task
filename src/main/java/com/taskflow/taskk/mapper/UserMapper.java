package com.taskflow.taskk.mapper;


// import statements -
import com.taskflow.taskk.dto.responseDto.UserResponseDto;
import com.taskflow.taskk.entity.User;

public class UserMapper {

    // method to convert User entity to UserResponseDto - 
    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setRoleName(user.getRole() != null ? user.getRole().getName() : null);
        userResponseDto.setActive(user.isActive());
        return userResponseDto;
    }
}
