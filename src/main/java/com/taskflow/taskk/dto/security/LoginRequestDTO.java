package com.taskflow.taskk.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
