package com.taskflow.taskk.service.serviceInterface;


import java.util.List;
import java.util.UUID;
import com.taskflow.taskk.dto.requestDto.LoginRequestDto;

// import statements -
import com.taskflow.taskk.dto.requestDto.UserRequestDto;
import com.taskflow.taskk.dto.responseDto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> fetchAllUsers();
        
    UserResponseDto fetchUserById(UUID id);

    UserResponseDto updateUserById(UUID id, UserRequestDto userRequestDto);

    void deleteUserById(UUID id);

    void activateUserAccount(UUID id);

    void deactivateUserAccount(UUID id);

    UserResponseDto fetchUserByEmail(String email);

    UserResponseDto validateCredentials(LoginRequestDto request);
}
