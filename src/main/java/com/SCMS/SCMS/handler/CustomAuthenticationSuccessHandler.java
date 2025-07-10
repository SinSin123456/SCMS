package com.SCMS.SCMS.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        logger.info("Login success for user: {}", authentication.getName());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/";

        for (GrantedAuthority authority : authorities) {
            logger.info("Authority: {}", authority.getAuthority());

            switch (authority.getAuthority()) {
                case "ROLE_STUDENT":
                    redirectUrl = "/student/dashboard";
                    break;
                case "ROLE_TEACHER":
                    redirectUrl = "/teacher/dashboard";
                    break;
                case "ROLE_ADMIN":
                    redirectUrl = "/admin/dashboard";
                    break;
            }
        }

        logger.info("Redirecting to: {}", redirectUrl);
        response.sendRedirect(redirectUrl + "?loginSuccess=true");

    }
}
