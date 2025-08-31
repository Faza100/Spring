package com.spring_boot_MVC.model;

public class Pet {
    private Long id;
    private String name;
    private Long userId;

    public Pet(Long id, String name,
            Long userId) {
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
