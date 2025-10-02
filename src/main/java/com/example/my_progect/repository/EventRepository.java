package com.example.my_progect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_progect.model.Event;
import com.example.my_progect.model.User;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByOwner(User owner);

}