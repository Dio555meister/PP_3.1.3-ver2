package com.UsersMVC.users.services;

import com.UsersMVC.users.models.Role;
import com.UsersMVC.users.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {


    private final RoleRepository roleRepository; // Репозиторий работы с ролями

    // Конструктор  внедряющий зависимость от RoleRepository через аннотацию @Autowired.
    // Принимает репозиторий в качестве параметра и присваивает его полю roleRepository
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Метод findAll возвращает список всех ролей. Он использует метод findAll репозитория roleRepository для
// получения списка ролей из бд и возвращает его
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}

