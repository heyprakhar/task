package com.taskflow.taskk.config.security.authorization;

import com.taskflow.taskk.annotation.HasAnyPermission;
import org.springframework.aop.support.AopUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class HasAnyPermissionAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation invocation) {

        Method method = AopUtils.getMostSpecificMethod(invocation.getMethod(), invocation.getThis().getClass());

        HasAnyPermission hasAnyPermission = method.getAnnotation(HasAnyPermission.class);

        if (hasAnyPermission == null) {
            return new AuthorizationDecision(true);
        }

        Authentication currentAuthentication = authentication.get();

        if (currentAuthentication == null || !currentAuthentication.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        Set<String> requiredPermissions = Arrays.stream(hasAnyPermission.value()).collect(Collectors.toSet());

        boolean hasPermission =
                currentAuthentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(requiredPermissions::contains);

        return new AuthorizationDecision(hasPermission);
    }
}