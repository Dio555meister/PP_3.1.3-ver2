package com.UsersMVC.users.controllers;

import com.UsersMVC.users.repositories.UserRepository;
import com.UsersMVC.users.services.UserDetailsServiceImpl;
import com.UsersMVC.users.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserDetailsServiceImpl userRepository; // Реализация сервиса пользователей

    public UserController(UserDetailsServiceImpl userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping() // метод обрабатывает GET-запросы.
    public String show(Model model, Principal principal) {
        // Добавление атрибута "user" в модель, который содержит информацию о пользователе с именем, полученным из Principal
        model.addAttribute("user", userRepository.loadUserByUsername(principal.getName()));
        return "users/user"; // Возвращение имени представления "users/user"
    }
}