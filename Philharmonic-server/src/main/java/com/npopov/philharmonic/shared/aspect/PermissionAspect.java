package com.npopov.philharmonic.shared.aspect;

import com.npopov.philharmonic.shared.annotation.RequirePermission;
import com.npopov.philharmonic.shared.security.CustomPermissionEvaluator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    private final CustomPermissionEvaluator permissionEvaluator;

    public PermissionAspect(CustomPermissionEvaluator permissionEvaluator) {
        this.permissionEvaluator = permissionEvaluator;
    }

    @Around("@annotation(requirePermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, RequirePermission requirePermission) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String requiredPermission = requirePermission.value();

        if (!permissionEvaluator.hasPermission(authentication, requiredPermission)) {
            throw new AccessDeniedException("Access denied: missing " + requiredPermission + " permission");
        }

        return joinPoint.proceed();
    }
}