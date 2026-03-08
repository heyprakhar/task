package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.dto.requestDto.UserRequestDto;
import com.taskflow.taskk.dto.responseDto.UserResponseDto;
import com.taskflow.taskk.entity.Role;
import com.taskflow.taskk.entity.User;
import com.taskflow.taskk.mapper.UserMapper;
import com.taskflow.taskk.repository.RoleRepository;
import com.taskflow.taskk.repository.UserRepository;
import com.taskflow.taskk.service.serviceInterface.UserService;
import java.util.List;
import java.util.stream.Collectors;

import static com.taskflow.taskk.commonUtils.Constants.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    // Create a new user with the default role- 
    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {

        log.info("Creating user with email: {}", requestDto.getEmail());

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            log.warn("User creation failed. Email already exists: {}", requestDto.getEmail());
            throw new RuntimeException("Email already exists");
        }

        log.debug("Fetching default role: {}", ROLE_USER);

        Role role = roleRepository.findByName(ROLE_USER)
                .orElseThrow(() -> {
                    log.error("Default role {} not found in database", ROLE_USER);
                    return new RuntimeException("Role not found");
                });

        log.debug("Role found: {}", role.getName());

        User user = new User();
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setRole(role);

        log.debug("Saving user: {}", user.getEmail());

        User savedUser = userRepository.save(user);

        log.info("User created successfully with ID: {}", savedUser.getId());

        return UserMapper.toUserResponseDto(savedUser);
    }

    //fetch all users -
    public List<UserResponseDto> fetchAllUsers() {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(UserMapper::toUserResponseDto)
                    .collect(Collectors.toList());  
    }
}