package com.UsersMVC.users.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Переопределение метода onAuthenticationSuccess из интерфейса AuthenticationSuccessHandler.
    // Этот метод вызывается при успешной аутентификации пользователя.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // Получение списка ролей пользователя из аутентификации
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Проверка наличия роли "ROLE_ADMIN"
        if (roles.contains("ROLE_ADMIN")) {
            // Если пользователь имеет роль "ROLE_ADMIN", перенаправляем его на страницу "/admin"
            httpServletResponse.sendRedirect("/admin");
        }
        // Проверка наличия роли "ROLE_USER"
        else if (roles.contains("ROLE_USER")) {
            // Если пользователь имеет роль "ROLE_USER", перенаправляем его на страницу "/user"
            httpServletResponse.sendRedirect("/user");
        }
        // Если пользователь не имеет ни роли "ROLE_ADMIN", ни "ROLE_USER"
        else {
            // Перенаправляем пользователя на главную страницу, представленную "/"
            httpServletResponse.sendRedirect("/");
        }
    }
}