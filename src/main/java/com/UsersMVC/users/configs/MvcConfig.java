package com.UsersMVC.users.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // Метод addViewControllers  для добавления пользовательских контроллеров представлений
    // Переопределяется из интерфейса WebMvcConfigurer
    public void addViewControllers(ViewControllerRegistry registry) {
        // добавляем пользовательские контроллеры представлений
        // Позволяют установить соответствие между URL и представлением без необходимости создания отдельного контроллера

    }
}
