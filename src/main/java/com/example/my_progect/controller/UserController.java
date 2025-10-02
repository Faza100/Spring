package com.example.my_progect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_progect.dto.user.UserRequestDto;
import com.example.my_progect.dto.user.UserResponseDto;
import com.example.my_progect.model.User;
import com.example.my_progect.service.UserService;
import com.example.my_progect.service.converter.UserConverter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public final UserService userService;
    public final UserConverter userConverter;
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    public UserController(
            UserService userService,
            UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userConverter.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody @Valid UserRequestDto createUserDto) {
        log.info("User create event request: {}", createUserDto);
        User user = userConverter.toEntity(createUserDto);
        User newUser = userService.createUser(user);
        log.info("User created successfully: {}", createUserDto);
        return ResponseEntity.ok(userConverter.toDto(newUser));
    }

}
