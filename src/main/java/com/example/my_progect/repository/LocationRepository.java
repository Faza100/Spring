package com.example.my_progect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_progect.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
