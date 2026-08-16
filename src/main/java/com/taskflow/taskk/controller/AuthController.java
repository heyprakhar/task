package com.taskflow.taskk.controller;


import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.security.LoginRequestDTO;
import com.taskflow.taskk.dto.security.LoginResponseDTO;
import com.taskflow.taskk.service.serviceInterface.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<BaseApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){

        LoginResponseDTO response = authenticationService.login(loginRequestDTO);

        return ResponseEntity.ok(
                BaseApiResponse.<LoginResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("User logged in successfully")
                        .data(response)
                        .build()
        );
    }
}
