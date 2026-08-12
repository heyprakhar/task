package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;

import java.util.List;

public interface UserService {
    UserDTO createUser(String userEmail, UserDTO userDTO);

    UserDTO getUserById(String userEmail, Long userId);

    ListResponseDTO<UserDTO> getAllUsers(String userEmail, ParamRequest request);

    UserDTO updateUser(String userEmail, UserDTO userDTO, Long userId);

    UserDTO getUserByEmailInternal(String userEmail);

    UserDTO getUserByIdInternal(Long id);

    ListResponseDTO<UserDTO> deactivateUsersByUserIds(List<Long> userIds,String userEmail);

    ListResponseDTO<UserDTO> activateUsersByUserIds(List<Long> userIds, String userEmail);

    List<Long> getUserIdsByRoleId(Long roleId);
}
