package com.UsersMVC.users.configs;

import com.UsersMVC.users.models.User;
import com.UsersMVC.users.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity(debug = true) //  вкл поддержку безопасности веб-приложения
@EnableTransactionManagement // включает поддержку управления транзакциями

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler; // Обработчик успешной аутен-ии пользователя
    private final UserServiceImpl userService; // Делаем сервис пользователей

    // Конструктор класса WebSecurityConfig, использующий внедрение зависимостей для получения экземпляров successUserHandler и userService.
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    // Переопределение метода configure для настройки HTTP-безопасности.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Определение прав доступа к URL-адресам с  "/user/**" для ролей "USER" и "ADMIN"
                .antMatchers("/admin/**").hasRole("ADMIN") // Определение прав доступа к URL-адресам с префиксом "/admin/**" только для роли "ADMIN"
                .anyRequest().authenticated() // Требование аутентификации для всех остальных URL-адресов
                .and()
                .formLogin().successHandler(successUserHandler) // Конфигурация формы входа и установка обработчика успешной аутентификации.
                .permitAll() // Разрешение доступа всем пользователям к форме входа
                .and()
                .logout()
                .permitAll(); // Разрешение доступа всем пользователям к форме выхода
    }

    // Создание и настройка объекта DaoAuthenticationProvider, используемого для аутентификации пользователей
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Настройка кодировщика паролей
        daoAuthenticationProvider.setUserDetailsService(userDetailsService()); // Установка сервиса пользователей
        return daoAuthenticationProvider;
    }

    // Создание и возвращение объекта PasswordEncoder, используемого для кодирования паролей пользователей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

