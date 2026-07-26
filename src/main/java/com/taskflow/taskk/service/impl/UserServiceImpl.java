package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.entity.User;
import com.taskflow.taskk.exceptions.BusinessException;
import com.taskflow.taskk.exceptions.ErrorCode;
import com.taskflow.taskk.mapper.UserMapper;
import com.taskflow.taskk.repository.UserRepository;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.UserService;
import com.taskflow.taskk.specification.UserSpecification;
import com.taskflow.taskk.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(String userEmail, UserDTO userDTO) {

        log.info("creating user");
        UserDTO loggedInUser = getUserByEmailInternal(userEmail);

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE, "User already exists with this email");
        }

        User newUser = UserMapper.toEntity(userDTO);
        newUser.setCreatedBy(loggedInUser.getId());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(newUser);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserDTO getUserById(String userEmail, Long userId) {
        log.info("getting user by id");
        getUserByEmailInternal(userEmail);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "User with provided ID not found"));

        return UserMapper.toDto(user);
    }

    @Override
    public ListResponseDTO<UserDTO> getAllUsers(String userEmail, ParamRequest request) {
        log.info("getting all users");

        getUserByEmailInternal(userEmail);

        List<UserDTO> userDTOS = List.of();

        Specification<User> spec = Specification.allOf(
                UserSpecification.hasActiveStatus(request.getActiveUser()),
                UserSpecification.hasRoleId(request.getUserRoleId()),
                UserSpecification.search(request.getSearch())
        );


        if(request.isRecords()){
            request.setSortBy(request.getSortBy() == null ? "name" : request.getSortBy());

            Page<User> users = userRepository.findAll(spec, CommonUtils.buildPageRequest(request));

            userDTOS = users.getContent()
                    .stream()
                    .map(UserMapper::toDto)
                    .toList();
        }

        return ListResponseDTO.<UserDTO>builder()
                .total(userRepository.count(spec))
                .records(userDTOS)
                .build();
    }


    @Override
    public UserDTO updateUser(String userEmail, UserDTO userDTO, Long userId) {

        log.info("updating user");

        getUserByEmailInternal(userEmail);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "User with provided ID not found"));

        String email = StringUtils.trimToNull(userDTO.getEmail());

        if (email != null && !email.equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE, "User already exists with this email");
        }

        if(userDTO.getEmail() != null){
            userDTO.setEmail(email);
        }

        UserMapper.updateEntity(userDTO, user);
        User updatedUser = userRepository.save(user);

        return UserMapper.toDto(updatedUser);
    }



    @Override
    public UserDTO getUserByEmailInternal(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "User not found"));

        return UserMapper.toDto(user);
    }

}
