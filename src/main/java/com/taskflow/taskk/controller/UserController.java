package com.taskflow.taskk.controller;

import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.taskflow.taskk.common.utils.Constants.HEADER_USERID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<UserDTO>> createUser(@RequestHeader(HEADER_USERID) String userEmail, @RequestBody UserDTO userDTO){

        UserDTO response = userService.createUser(userEmail, userDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseApiResponse.<UserDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("User created successfully")
                        .data(response)
                        .build()
                );
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse<ListResponseDTO<UserDTO>>> getAllUsers(@RequestHeader(HEADER_USERID) String userEmail, ParamRequest request){

        ListResponseDTO<UserDTO> response = userService.getAllUsers(userEmail, request);

        return ResponseEntity.ok(BaseApiResponse.<ListResponseDTO<UserDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Users fetched successfully")
                .data(response)
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<UserDTO>> getUserById(@RequestHeader(HEADER_USERID) String userEmail,@PathVariable Long id){

        UserDTO response = userService.getUserById(userEmail, id);

        return ResponseEntity.ok(
                BaseApiResponse.<UserDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("User fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<UserDTO>> updateUser(@RequestHeader(HEADER_USERID) String userEmail, @PathVariable Long id, @RequestBody UserDTO userDTO) {

        UserDTO response = userService.updateUser(userEmail, userDTO, id);

        return ResponseEntity.ok(
                BaseApiResponse.<UserDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("User updated successfully")
                        .data(response)
                        .build()
        );
    }
}
