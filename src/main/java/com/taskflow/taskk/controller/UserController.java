package com.taskflow.taskk.controller;


// import statements - 
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.taskflow.taskk.dto.requestDto.UserRequestDto;
import com.taskflow.taskk.dto.responseDto.UserResponseDto;
import com.taskflow.taskk.service.serviceInterface.UserService;
import com.taskflow.taskk.common.response.BaseApiResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<BaseApiResponse<UserResponseDto>> createUser(
            @RequestBody UserRequestDto userRequestDto) {

        UserResponseDto userResponseDto = userService.createUser(userRequestDto);

        BaseApiResponse<UserResponseDto> response = new BaseApiResponse<>(true, "User created successfully",
                userResponseDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}