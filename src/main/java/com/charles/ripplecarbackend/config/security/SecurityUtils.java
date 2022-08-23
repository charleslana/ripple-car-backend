package com.charles.ripplecarbackend.config.security;

import com.charles.ripplecarbackend.model.dto.UserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getAuthEmail() {
        return ((UserDetailsDTO) getAuth().getPrincipal()).getUsername();
    }

    private static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
