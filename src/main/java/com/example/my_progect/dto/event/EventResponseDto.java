package com.example.my_progect.dto.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.my_progect.model.enums.EventStatus;

public class EventResponseDto {

    private Long id;

    private String name;

    private Long ownerId;

    private Integer maxPlaces;

    private Integer occupiedPlaces = 0;

    private LocalDateTime date;

    private Integer duration;

    private BigDecimal cost;

    private Long locationId;

    private EventStatus status;

    public EventResponseDto(
            LocalDateTime date,
            Integer duration,
            BigDecimal cost,
            Integer maxPlaces,
            Long locationId,
            String name) {
        this.date = date;
        this.duration = duration;
        this.cost = cost;
        this.maxPlaces = maxPlaces;
        this.locationId = locationId;
        this.name = name;
    }

    public EventResponseDto(
            Integer occupiedPlaces,
            LocalDateTime date,
            Integer duration,
            BigDecimal cost,
            Integer maxPlaces,
            Long locationId,
            String name,
            Long id,
            Long ownerId,
            EventStatus status) {
        this.occupiedPlaces = occupiedPlaces;
        this.date = date;
        this.duration = duration;
        this.cost = cost;
        this.maxPlaces = maxPlaces;
        this.locationId = locationId;
        this.name = name;
        this.id = id;
        this.ownerId = ownerId;
        this.status = status;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(Integer maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Integer getOccupiedPlaces() {
        return occupiedPlaces;
    }

    public void setOccupiedPlaces(Integer occupiedPlaces) {
        this.occupiedPlaces = occupiedPlaces;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EventStatus getEventStatus() {
        return status;
    }

    public void setEventStatus(EventStatus status) {
        this.status = status;
    }
}
