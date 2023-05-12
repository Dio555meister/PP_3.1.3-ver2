package com.UsersMVC.users.services;

import com.UsersMVC.users.models.Role;
import com.UsersMVC.users.models.User;
import com.UsersMVC.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository peopleRepository;


    public UserServiceImpl(UserRepository peopleRepository) {

        this.peopleRepository = peopleRepository;
    }

    @Override
    public List<User> index() {
        return peopleRepository.findAll();
    }

    @Override
    public User show(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(@Valid User person) {

        peopleRepository.save(person);
    }

    @Override
    @Transactional
    public void delete(int id) {

        peopleRepository.deleteById(id);
    }





    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
