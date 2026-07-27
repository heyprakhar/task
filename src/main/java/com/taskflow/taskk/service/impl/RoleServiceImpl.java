package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.dto.RoleDTO;
import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.entity.Role;
import com.taskflow.taskk.exceptions.BusinessException;
import com.taskflow.taskk.exceptions.ErrorCode;
import com.taskflow.taskk.mapper.RoleMapper;
import com.taskflow.taskk.repository.RoleRepository;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.RoleService;
import com.taskflow.taskk.service.serviceInterface.UserService;
import com.taskflow.taskk.specification.RoleSpecification;
import com.taskflow.taskk.utils.CommonUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserService userService;
    private final RoleRepository roleRepository;


    @Override
    public ListResponseDTO<RoleDTO> getAllRoles(String userEmail, ParamRequest request) {

        log.info("Getting all roles");
        userService.getUserByEmailInternal(userEmail);

        List<RoleDTO> records = List.of();

        Specification<Role> spec = Specification.allOf(
                RoleSpecification.search(request.getSearch()),
                RoleSpecification.hasActiveStatus(request.getActive())
        );

        if (request.isRecords()){
            request.setSortBy(request.getSortBy() == null ? "name" : request.getSortBy());

            Page<Role> roles = roleRepository.findAll(spec, CommonUtils.buildPageRequest(request));

            records = roles.getContent()
                    .stream()
                    .map(RoleMapper::toDto)
                    .toList();
        }

        return ListResponseDTO.<RoleDTO>builder()
                .total(roleRepository.count(spec))
                .records(records)
                .build();
    }

    @Override
    public RoleDTO getRoleById(String userEmail, Long roleId) {
        log.info("Getting role by id: {}", roleId);

       userService.getUserByEmailInternal(userEmail);

      Role role = roleRepository.findById(roleId)
              .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "Role with provided identifier not found"));


      Role savedRole = roleRepository.save(role);

      return RoleMapper.toDto(savedRole);
    }

    @Override
    public RoleDTO createRole(String userEmail, RoleDTO roleDTO) {
        log.info("Creating role: {}", roleDTO);
        UserDTO user =  userService.getUserByEmailInternal(userEmail);
        Role role = RoleMapper.toEntity(roleDTO);
        role.setCreatedBy(user.getId());
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toDto(savedRole);
    }

    @Override
    @Transactional
    public RoleDTO updateRole(String userEmail, Long roleId, RoleDTO roleDTO) {
        log.info("Updating role with id: {}", roleId);

        UserDTO user = userService.getUserByEmailInternal(userEmail);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Role not found."));

        roleRepository.findByName(roleDTO.getName())
                .filter(existing -> !existing.getId().equals(roleId))
                .ifPresent(existing -> {
                    throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE, "Role with name '" + roleDTO.getName() + "' already exists.");
                });

        RoleMapper.updateEntity(role, roleDTO);
        role.setUpdatedBy(user.getId());

        Role savedRole = roleRepository.save(role);

        return RoleMapper.toDto(savedRole);
    }
}
