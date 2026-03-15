package com.taskflow.taskk.controller;


// import statements - 
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.taskflow.taskk.dto.requestDto.LoginRequestDto;
import com.taskflow.taskk.dto.requestDto.UserRequestDto;
import com.taskflow.taskk.dto.responseDto.UserResponseDto;
import com.taskflow.taskk.service.serviceInterface.UserService;
import com.taskflow.taskk.common.response.BaseApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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
        BaseApiResponse<UserResponseDto> response = new BaseApiResponse<>(true, "User fetched successfully",
                userResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //update user details endpoint-
    @PatchMapping("/update-user/{id}")
     public ResponseEntity<BaseApiResponse<UserResponseDto>> updateUserById(@PathVariable UUID id, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.updateUserById(id, userRequestDto);
        BaseApiResponse<UserResponseDto> response = new BaseApiResponse<>(true, "User updated successfully",
                updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
     }

     // delete user by Id endpoint- 
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<BaseApiResponse<Void>> deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
        BaseApiResponse<Void> response = new BaseApiResponse<>(true, "User deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
    
    // activate user account endpoint-
    @PatchMapping("/{id}/activate")
    public ResponseEntity<BaseApiResponse<Void>> activateUserAccount(@PathVariable UUID id) {
        userService.activateUserAccount(id);
        BaseApiResponse<Void> response = new BaseApiResponse<>(true, "User account activated successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // deactivate user account endpoint-
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<BaseApiResponse<Void>> deactivateUserAccount(@PathVariable UUID id) {
        userService.deactivateUserAccount(id);
        BaseApiResponse<Void> response = new BaseApiResponse<>(true, "User account deactivated successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // fetch user by email endpoint-
    @GetMapping("/email/{email}")
    public ResponseEntity<BaseApiResponse<UserResponseDto>> getUserByEmail(@PathVariable String email) {
        UserResponseDto userResponseDto = userService.fetchUserByEmail(email);
        BaseApiResponse<UserResponseDto> response = new BaseApiResponse<>(true, "User fetched successfully",
                userResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // validate user credentials endpoint-
    @PostMapping("/validate-credentials")
public BaseApiResponse<UserResponseDto> validateCredentials(@RequestBody LoginRequestDto request){

    UserResponseDto user = userService.validateCredentials(request);

    return new BaseApiResponse<>(true,"Valid credentials", user );
}
    
}