package com.example.my_progect.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_progect.dto.event.EventCreateRequestDto;
import com.example.my_progect.dto.event.EventResponseDto;
import com.example.my_progect.dto.event.EventUpdateRequestDto;
import com.example.my_progect.model.Event;
import com.example.my_progect.service.EventService;
import com.example.my_progect.service.converter.EventConverter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final EventConverter eventConverter;
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    public EventController(
            EventService eventService,
            EventConverter eventConverter) {
        this.eventService = eventService;
        this.eventConverter = eventConverter;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvent() {
        List<Event> events = eventService.getAllEvent();
        List<EventResponseDto> eventDtos = events.stream()
                .map(eventConverter::toDto)
                .toList();
        return ResponseEntity.ok(eventDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(
            @PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(eventConverter.toDto(event));
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(
            @RequestBody @Valid EventCreateRequestDto createEventDto) {
        log.info("Received create event request: {}", createEventDto);
        Event createEvent = eventService.createEvent(eventConverter.toCreateEntity(createEventDto));
        EventResponseDto response = eventConverter.toDto(createEvent);
        log.info("Event created successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable Long id,
            @RequestBody @Valid EventUpdateRequestDto updateEventDto) {
        log.info("Received update event request for id {}: {}", id, updateEventDto);
        Event event = eventConverter.toUpdateEntity(updateEventDto);
        Event updatedEvent = eventService.updateEvent(id, event);
        EventResponseDto response = eventConverter.toDto(updatedEvent);
        log.info("Event update successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id) {
        eventService.deleteEventByid(id);
        return ResponseEntity.ok().build();
    }

}
