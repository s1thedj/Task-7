package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        Long id = null;
        if(principal instanceof User) {
            id = (((User) principal).getId());
            System.out.println("Authenticated user: " + ((User) principal).getId());
        }
        if (roles.contains("ROLE_ADMIN")) {
            System.out.println("REDIRECTING ADMIN");
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            System.out.println("REDIRECTING USER");
            httpServletResponse.sendRedirect("/user?id="+id);
        } else {
            System.err.println("No matching role found");
        }
    }
}