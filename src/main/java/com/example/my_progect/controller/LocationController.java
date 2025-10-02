package com.example.my_progect.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_progect.dto.location.LocationCreateRequestDto;
import com.example.my_progect.dto.location.LocationResponseDto;
import com.example.my_progect.model.Location;
import com.example.my_progect.service.LocationServise;
import com.example.my_progect.service.converter.LocationConverter;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationServise locationServise;
    private final LocationConverter locationConverter;
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    public LocationController(
            LocationServise locationServise,
            LocationConverter locationConverter) {
        this.locationServise = locationServise;
        this.locationConverter = locationConverter;
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDto>> getAllLocation() {
        List<Location> locations = locationServise.getAllLocation();
        List<LocationResponseDto> locationDtos = locations.stream()
                .map(locationConverter::toDto)
                .toList();
        return ResponseEntity.ok(locationDtos);
    }

    @PostMapping
    public ResponseEntity<LocationResponseDto> createLocation(
            @RequestBody @Valid LocationCreateRequestDto locationCreateRequestDto) {
        log.info("Location create event request: {}", locationCreateRequestDto);
        Location locations = locationServise.createLocation(
                locationConverter.toEntity(locationCreateRequestDto));
        LocationResponseDto response = locationConverter.toDto(locations);
        log.info("Location create successfully: {}", locationCreateRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDto> getLocationById(
            @PathVariable Long id) {
        Location locations = locationServise.getUserById(id);
        return ResponseEntity.ok(locationConverter.toDto(locations));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationById(
            @PathVariable Long id) {
        locationServise.deleteLocationByid(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDto> updateLocation(
            @PathVariable Long id,
            @RequestBody @Valid LocationCreateRequestDto locationUpdateRequestDto) {
        log.info("Received update location request for id {}: {}", id, locationUpdateRequestDto);
        Location locations = locationServise.updateLocation(id,
                locationConverter.toEntity(locationUpdateRequestDto));
        LocationResponseDto response = locationConverter.toDto(locations);
        log.info("Location update successfully: {}", response);
        return ResponseEntity.ok(response);
    }
}
