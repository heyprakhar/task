package com.taskflow.taskk.service.serviceInterface;


import com.taskflow.taskk.dto.requestDto.LoginRequestDto;
import com.taskflow.taskk.dto.requestDto.UserRequestDto;
import com.taskflow.taskk.dto.responseDto.UserResponseDto;

import java.util.List;

// import statements -

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> fetchAllUsers();
        
    UserResponseDto fetchUserById(Long id);

    UserResponseDto updateUserById(Long id, UserRequestDto userRequestDto);

    void deleteUserById(Long id);

    void activateUserAccount(Long id);

    void deactivateUserAccount(Long id);

    UserResponseDto fetchUserByEmail(String email);

    UserResponseDto validateCredentials(LoginRequestDto request);
}
