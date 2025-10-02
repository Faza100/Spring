package com.example.my_progect.dto.user;

import java.util.List;

import com.example.my_progect.model.enums.UserRole;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRequestDto {

    private Long id;

    @NotBlank(message = "Login is required")
    @Size(min = 2, max = 15, message = "Name must be between 2 and 15 characters")
    private String login;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "You must be of legal age")
    @Max(value = 100, message = "Age must be realistic")
    private Integer age;

    @NotBlank(message = "Password is required")
    @Min(value = 6, message = "The password cannot be less than 6 characters")
    @Max(value = 25, message = "The password cannot be longer than 25 characters")
    private String password;

    private UserRole role;

    private List<Long> ownerEventIds;

    private List<Long> participatingEventIds;

    public UserRequestDto(String login,
            String password, Integer age) {
        this.login = login;
        this.password = password;
        this.age = age;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Long> getOwnerEventIds() {
        return ownerEventIds;
    }

    public void setOwnerEventIds(List<Long> ownerEventIds) {
        this.ownerEventIds = ownerEventIds;
    }

    public List<Long> getParticipatingEventIds() {
        return participatingEventIds;
    }

    public void setParticipatingEventIds(List<Long> participatingEventIds) {
        this.participatingEventIds = participatingEventIds;
    }

}
