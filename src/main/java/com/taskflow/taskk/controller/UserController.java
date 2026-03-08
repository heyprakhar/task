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
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;

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

    // fetch all user endpoint-
@GetMapping("/fetch-all-users")
    public ResponseEntity<BaseApiResponse<List<UserResponseDto>>> getAllUsers() {
        List<UserResponseDto> users = userService.fetchAllUsers();
        BaseApiResponse<List<UserResponseDto>> response = new BaseApiResponse<>(true, "Users fetched successfully", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //fetch user by Id endpoint-
    @GetMapping("/fetch-user/{id}")
    public ResponseEntity<BaseApiResponse<UserResponseDto>> getUserById(@PathVariable UUID id) {
        UserResponseDto userResponseDto = userService.fetchUserById(id);
        BaseApiResponse<UserResponseDto> response = new BaseApiResponse<>(true, "User fetched successfully", userResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);   
}
}