package com.npopov.philharmonic.shared.security;

import com.npopov.philharmonic.identity.user.service.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("customPermissionEvaluator")
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final UserService userService;

    public CustomPermissionEvaluator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return hasPermission(authentication, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return hasPermission(authentication, permission);
    }

    public boolean hasPermission(Authentication authentication, Object permission) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String username = authentication.getName();
        return userService.findByUsername(username)
                .map(user -> user.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .anyMatch(p -> p.name().equals(permission.toString())))
                .orElse(false);
    }
}