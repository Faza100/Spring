package com.example.my_progect.service.converter;

import org.springframework.stereotype.Service;

import com.example.my_progect.dto.event.EventCreateRequestDto;
import com.example.my_progect.dto.event.EventResponseDto;
import com.example.my_progect.dto.event.EventUpdateRequestDto;
import com.example.my_progect.model.Event;
import com.example.my_progect.model.Location;
import com.example.my_progect.repository.LocationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventConverter {

    public final LocationRepository locationRepository;

    public EventConverter(
            LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Event toCreateEntity(EventCreateRequestDto LocationDto) {
        return new Event(
                LocationDto.getDate(),
                LocationDto.getDuration(),
                LocationDto.getCost(),
                LocationDto.getMaxPlaces(),
                convertLocationIdsToEntity(LocationDto.getLocationId()),
                LocationDto.getName());
    }

    public EventResponseDto toDto(Event event) {
        return new EventResponseDto(
                event.getOccupiedPlaces(),
                event.getDate(),
                event.getDuration(),
                event.getCost(),
                event.getMaxPlaces(),
                event.getLocation().getId(),
                event.getName(),
                event.getId(),
                event.getOwner().getId(),
                event.getEventStatus());
    }

    public Event toUpdateEntity(EventUpdateRequestDto LocationDto) {
        return new Event(
                LocationDto.getDate(),
                LocationDto.getDuration(),
                LocationDto.getCost(),
                LocationDto.getMaxPlaces(),
                convertLocationIdsToEntity(LocationDto.getLocationId()),
                LocationDto.getName());
    }

    private Location convertLocationIdsToEntity(
            Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + locationId));
    }
}
