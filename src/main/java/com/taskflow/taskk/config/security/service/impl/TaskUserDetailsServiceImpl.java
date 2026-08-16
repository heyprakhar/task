package com.taskflow.taskk.config.security.service.impl;

import com.taskflow.taskk.config.security.CustomUserDetails;
import com.taskflow.taskk.config.security.service.TaskUserDetailsService;
import com.taskflow.taskk.dto.RoleDTO;
import com.taskflow.taskk.entity.User;
import com.taskflow.taskk.repository.UserRepository;
import com.taskflow.taskk.service.serviceInterface.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskUserDetailsServiceImpl implements TaskUserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        RoleDTO roleDTO = roleService.getRoleByIdInternal(user.getRoleId());

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+ roleDTO.getName());

        return new CustomUserDetails(user, roleDTO.getActive(), List.of(grantedAuthority));
    }
}