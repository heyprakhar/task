package com.taskflow.taskk.config.security;

import com.taskflow.taskk.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String email;
    private final String name;
    private final String password;
    private final Long roleId;
    private final boolean active;
    private final Boolean roleActive;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user, Boolean roleActive, Collection<? extends GrantedAuthority> authorities) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.roleId = user.getRoleId();
        this.active = user.isActive();
        this.roleActive = roleActive;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active && Boolean.TRUE.equals(roleActive);
    }
}