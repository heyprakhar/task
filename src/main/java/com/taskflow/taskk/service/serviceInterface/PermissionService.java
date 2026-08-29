package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface PermissionService {

    Collection<? extends GrantedAuthority> getEffectiveAuthorities(User user);

    boolean hasAnyPermission(String... permissions);
}