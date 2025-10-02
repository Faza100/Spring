package com.example.my_progect.dto.user;

import com.example.my_progect.model.enums.UserRole;

public class UserResponseDto {

    private Long id;

    private String login;

    private Integer age;

    private String password;

    private UserRole role;

    public UserResponseDto(
            Long id,
            String login,
            String password,
            Integer age,
            UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.age = age;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setName(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserRole getListOfEventsID() {
        return role;
    }

    public void setListOfEventId(UserRole role) {
        this.role = role;
    }

}
