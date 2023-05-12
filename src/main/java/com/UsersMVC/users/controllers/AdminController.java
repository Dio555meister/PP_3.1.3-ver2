package com.UsersMVC.users.controllers;


import com.UsersMVC.users.models.User;
import com.UsersMVC.users.services.RoleService;
import com.UsersMVC.users.services.UserServiceImpl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")  // наш путь для обработки запросов
public class AdminController {

    private final UserServiceImpl userServiceImp; // Реализация сервиса пользователей
    public final RoleService roleService; // Сервис ролей

    // Конструктор класса AdminController, использующий внедрение зависимостей для получения экземпляров userService и roleService.
    public AdminController(UserServiceImpl userService, RoleService roleService) {
        this.userServiceImp = userService;
        this.roleService = roleService;
    }

    // Обработчик GET запроса для отображения главной страницы администратора
    @GetMapping
    public String index(Model model) {
        // Добавление атрибута "users" в модель, который содержит список пользователей, полученный через userServiceImp.index().
        model.addAttribute("users", userServiceImp.index());

        return "users/index"; // Возвращение имени представления "users/index"
    }

    // Обработчик GET запроса для отображения страницы информации о пользователе по его идентификатору
    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        // Проверка, является ли переданный идентификатор "favicon.ico"
        if (id.equals("favicon.ico")) {
            return "redirect:/admin"; // Перенаправление на главную страницу администратора
        }
        // Добавление атрибута "user" в модель, который содержит информацию о пользователе с заданным идентификатором
        model.addAttribute("user", userServiceImp.show(Integer.parseInt(id)));
        return "users/show"; // Возвращение имени представления "users/show"
    }

    // Обработчик GET-запроса для отображения страницы создания нового пользователя
    @GetMapping("/new")
    public String newPerson(Model model) {
        // Добавление атрибутов "user" и "roles" в модель.
        // "user" содержит новый экземпляр класса User, который будет использован для создания нового пользователя
        // "roles" содержит список всех ролей, полученный через roleService.findAll()
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "users/new"; // Возвращение имени представления "users/new"
    }

    // Обработчик POST-запроса для создания нового пользователя
    @PostMapping()
    public String creat(@ModelAttribute("user") @Valid User person,
                        BindingResult bindingResult) {
        // Проверка наличия ошибок валидации при создании пользователя
        if (bindingResult.hasErrors())
            return "users/new"; // Если есть ошибки, возвращаем имя представления "users/new"
        userServiceImp.save(person); // Сохранение созданного пользователя через userServiceImp.save()
        return "redirect:/admin"; // Перенаправление на главную страницу администратор

    }
}