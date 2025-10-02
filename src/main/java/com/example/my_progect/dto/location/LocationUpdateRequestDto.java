package com.example.my_progect.dto.location;

import jakarta.validation.constraints.Size;

public class LocationUpdateRequestDto {

    private Long id;

    @Size(min = 2, max = 80, message = "Name location must be between 2 and 100 characters")
    private String name;

    private String address;

    @Size(min = 8, message = "The capacity of the location cannot be less than 8")
    private Integer capacity;

    private String description;

    public LocationUpdateRequestDto(
        String name,
        String address,
        Integer capacity,
        String description){
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
