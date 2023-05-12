package com.UsersMVC.users.models;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "поле не должны быть пустым")
    @Size(max = 35, message = "превышено количество символов")
    @Column(name = "username")
    private String name;
    @Min(value = 0, message = "Поле не может быть пустым")
    @Max(value = 60, message = "Максимальный возраст не более 60")
    @Column(name = "age")
    private int age;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Email(message = "Некоректный email")
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @ManyToMany // связь многие ко многим между сущностями
    @JoinTable(
            name = "users_roles", // Название таблицы связывания ролей и пользователей
            joinColumns = @JoinColumn(name = "user_id"), // Колонка "user_id" в таблице "users_roles"
            inverseJoinColumns = @JoinColumn(name = "role_id")) // Колонка "role_id" в таблице "users_roles"
    @Fetch(FetchMode.JOIN) // Указание режима загрузки "JOIN" для немедленной загрузки связанных сущностей
    private Set<Role> roles; // Список ролей, связанных с пользователем

    // Получаем список ролей пользователя
    public Set<Role> getRoles() {
        return roles;
    }

    // Установка списка ролей пользователя
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public User(String name, int age, String email, String password, Set<Role> roles) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public User() {
    }

    // возвращает коллекцию объектов `GrantedAuthority`, представляющих роли пользователя.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }


    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    //  истек ли срок действия учетной записи пользователя.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // заблокирована ли учетная запись пользователя
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // истек ли срок действия учетных данных пользователя
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //  включена ли учетная запись пользователя
    @Override
    public boolean isEnabled() {
        return true;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public User getUser() {
        return new User();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, password, roles);
    }

}