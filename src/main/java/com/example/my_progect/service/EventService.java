package com.example.my_progect.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_progect.model.Event;
import com.example.my_progect.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(
            EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + eventId));
    }

    public void deleteEventByid(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));
        eventRepository.delete(event);
    }

    public Event updateEvent(Long id, Event event) {
        Event existingEvent = eventRepository.findById(event.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + event.getId()));
        if (event.getName() != null) {
            existingEvent.setName(event.getName());
        }
        if (event.getDuration() != null) {
            existingEvent.setDuration(event.getDuration());
        }
        if (event.getCost() != null && event.getCost().compareTo(BigDecimal.ZERO) >= 0) {
            existingEvent.setCost(event.getCost());
        }
        if (event.getDate() != null) {
            existingEvent.setDate(event.getDate());
        }
        if (event.getEventStatus() != null) {
            existingEvent.setEventStatus(event.getEventStatus());
        }
        return eventRepository.save(event);
    }

}
