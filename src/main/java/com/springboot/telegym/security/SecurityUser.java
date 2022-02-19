package com.springboot.telegym.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUser {

    public static UserDetailsImpl identifyCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            return (UserDetailsImpl) principal;
        } else {
            return null;
        }
    }
}
