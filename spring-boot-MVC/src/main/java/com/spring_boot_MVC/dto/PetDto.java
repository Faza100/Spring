package com.spring_boot_MVC.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class PetDto {
    @Null(message = "ID must not be provided for creation")
    Long id;
    @NotBlank(message = "Pet name is required")
    @Size(min = 2, max = 12, message = "Pet name must be between 2 and 12 characters")
    String name;
    @Null(message = "userID must not be provided for creation")
    Long userId;

    public PetDto(Long id,
            String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
