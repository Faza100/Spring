package com.example.my_progect.dto.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.my_progect.model.enums.EventStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class EventUpdateRequestDto {

    private Long id;

    @Size(min = 2, max = 80, message = "Name event must be between 2 and 100 characters")
    private String name;

    @Min(value = 1, message = "Max places must be at least 1 minute")
    private Integer maxPlaces;

    @Future(message = "Event date must be in the future")
    private LocalDateTime date;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 1440, message = "Duration cannot exceed 1440 minutes (24 hours)")
    private Integer duration;

    @DecimalMin(value = "0.0", inclusive = true, message = "Cost cannot be negative")
    @Digits(integer = 10, fraction = 2, message = "Cost must have max 10 integer digits and 2 fraction digits")
    private BigDecimal cost;

    private Long locationId;

    private EventStatus status = EventStatus.WAIT_START;

    public EventUpdateRequestDto(
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
