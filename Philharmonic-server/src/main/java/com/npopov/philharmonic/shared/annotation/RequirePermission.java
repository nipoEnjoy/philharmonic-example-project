package com.npopov.philharmonic.shared.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks if user has permission (Permission).
 * Usage ex.: @RequirePermission("CREATE")
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

    /**
     * Name from enum Permission: "CREATE", "READ", "UPDATE", "DELETE".
     */
    String value();
}