package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.entity.Permission;
import com.taskflow.taskk.entity.RolePermission;
import com.taskflow.taskk.entity.User;
import com.taskflow.taskk.entity.UserPermission;
import com.taskflow.taskk.repository.PermissionRepository;
import com.taskflow.taskk.repository.RolePermissionRepository;
import com.taskflow.taskk.repository.UserPermissionRepository;
import com.taskflow.taskk.service.serviceInterface.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public Collection<? extends GrantedAuthority> getEffectiveAuthorities(User user) {

        Map<Long, Boolean> effectivePermissions = new HashMap<>();

        // get role permissions -
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleIdAndActiveTrue(user.getRoleId());

        for (RolePermission rolePermission : rolePermissions) {
            effectivePermissions.put(rolePermission.getPermissionId(), true);
        }

        // get user permissions -
        List<UserPermission> userPermissions = userPermissionRepository.findByUserIdAndActiveTrue(user.getId());

        for (UserPermission userPermission : userPermissions) {
            effectivePermissions.put(userPermission.getPermissionId(), userPermission.isGranted());
        }

        // construct effective permissions -
        List<Long> grantedPermissionIds =
                effectivePermissions.entrySet()
                        .stream()
                        .filter(Map.Entry::getValue)
                        .map(Map.Entry::getKey)
                        .toList();

        if (grantedPermissionIds.isEmpty()) {
            return List.of();
        }

        // fetch all effective permissions at once to avoid N+1 -
        List<Permission> permissions = permissionRepository.findByIdInAndActiveTrue(grantedPermissionIds);

        return permissions.stream()
                .map(permission -> (GrantedAuthority) new SimpleGrantedAuthority(permission.getName()))
                .toList();
    }

    @Override
    public boolean hasAnyPermission(String... permissions) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        if (permissions == null || permissions.length == 0) {
            return false;
        }

        Set<String> requiredPermissions = Set.of(permissions);

        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(requiredPermissions::contains);
    }
}