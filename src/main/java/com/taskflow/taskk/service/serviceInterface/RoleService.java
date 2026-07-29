package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.RoleDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;

public interface RoleService {
    ListResponseDTO<RoleDTO> getAllRoles(String userEmail, ParamRequest request);

    RoleDTO getRoleById(String userEmail, Long roleId);

    RoleDTO createRole(String userEmail, RoleDTO roleDTO);

    RoleDTO updateRole(String userEmail, Long roleId, RoleDTO roleDTO);

    String deleteRole(String userEmail, Long roleId);
}
