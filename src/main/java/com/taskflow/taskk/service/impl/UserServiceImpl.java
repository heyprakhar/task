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
import java.util.UUID;
import static com.taskflow.taskk.commonUtils.Constants.*;
import com.taskflow.taskk.dto.requestDto.LoginRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
     private final PasswordEncoder passwordEncoder;


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
    @Override
    public List<UserResponseDto> fetchAllUsers() {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(UserMapper::toUserResponseDto)
                    .collect(Collectors.toList());  
    }
    // fetch user by Id-
    public UserResponseDto fetchUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return UserMapper.toUserResponseDto(user);
    }

        
    
    // update user details-
        @Override
        public UserResponseDto updateUserById(UUID id, UserRequestDto userRequestDto){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            user.setName(userRequestDto.getName());
            user.setEmail(userRequestDto.getEmail());
            user.setPassword(userRequestDto.getPassword());
            User updatedUser = userRepository.save(user);
            return UserMapper.toUserResponseDto(updatedUser);
        }

        // delete user by Id- 
        @Override
        public void deleteUserById(UUID id){
            User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            userRepository.delete(user);
        }

        // activate user account -
        @Override
        public void activateUserAccount(UUID id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            if (user.isActive()) {
                log.warn("User account is already active for user ID: {}", id);
                throw new RuntimeException("User account is already active");
            }
            user.setActive(true);
            userRepository.save(user);
            log.info("User account activated successfully for user ID: {}", id);
        }

        // deactivate user account -
        @Override
        public void deactivateUserAccount(UUID id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            if (!user.isActive()) {
                log.warn("User account is already inactive for user ID: {}", id);
                throw new RuntimeException("User account is already inactive");
            }
            user.setActive(false);
            userRepository.save(user);
            log.info("User account deactivated successfully for user ID: {}", id);
        }

        // fetch user by email-
        @Override
        public UserResponseDto fetchUserByEmail(String email) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
            return UserMapper.toUserResponseDto(user);
        }

        // validate user credentials-
        @Override
        public UserResponseDto validateCredentials(LoginRequestDto request) {

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            return UserMapper.toUserResponseDto(user);
        }
}