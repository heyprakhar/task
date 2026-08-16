package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.RoleDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;
import jakarta.transaction.Transactional;

public interface RoleService {
    ListResponseDTO<RoleDTO> getAllRoles(String userEmail, ParamRequest request);

    RoleDTO getRoleByIdInternal(Long roleId);

    RoleDTO getRoleById(String userEmail, Long roleId);

    RoleDTO createRole(String userEmail, RoleDTO roleDTO);

    RoleDTO updateRole(String userEmail, Long roleId, RoleDTO roleDTO);

    RoleDTO deactivateRole(String userEmail,Long roleId);

    @Transactional
    RoleDTO activateRole(String userEmail, Long roleId, ParamRequest request);
}
