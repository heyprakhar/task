package com.taskflow.taskk.service.serviceInterface;

import org.hibernate.internal.build.AllowPrintStacktrace;

// import statements -
import com.taskflow.taskk.dto.requestDto.UserRequestDto;
import com.taskflow.taskk.dto.responseDto.UserResponseDto;

public interface UserService {
UserResponseDto createUser(UserRequestDto userRequestDto);
}
