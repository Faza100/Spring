package com.spring_boot_MVC.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class CreateUserDto {
    @Null(message = "ID must not be provided for creation")
    Long id;
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 15, message = "Name must be between 2 and 15 characters")
    String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;
    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be positive")
    @Max(value = 100, message = "Age must be realistic")
    Integer age;
    List<PetDto> pets = new ArrayList<>();

    public CreateUserDto(Long id, String name,
            String email, Integer age,
            List<PetDto> pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        if (pets != null) {
            this.pets.addAll(pets);
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setPets(List<PetDto> pets) {
        this.pets = pets;
    }

    public List<PetDto> getPets() {
        return pets;
    }
}
