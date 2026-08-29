package com.taskflow.taskk.config.security.service.impl;

import com.taskflow.taskk.config.security.CustomUserDetails;
import com.taskflow.taskk.config.security.service.TaskUserDetailsService;
import com.taskflow.taskk.dto.RoleDTO;
import com.taskflow.taskk.entity.User;
import com.taskflow.taskk.repository.UserRepository;
import com.taskflow.taskk.service.serviceInterface.PermissionService;
import com.taskflow.taskk.service.serviceInterface.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskUserDetailsServiceImpl implements TaskUserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        RoleDTO roleDTO = roleService.getRoleByIdInternal(user.getRoleId());

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Add role authority
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleDTO.getName()));

        // Add effective permission authorities
        authorities.addAll(permissionService.getEffectiveAuthorities(user));

        log.info("User [{}] authorities: {}", user.getEmail(), authorities);

        return new CustomUserDetails(user, roleDTO.getActive(), authorities);
    }
}