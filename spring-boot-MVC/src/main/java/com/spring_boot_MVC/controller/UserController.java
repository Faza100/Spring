package com.spring_boot_MVC.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_boot_MVC.converter.UserDtoConverter;
import com.spring_boot_MVC.dto.CreateUserDto;
import com.spring_boot_MVC.dto.UpdateUserDto;
import com.spring_boot_MVC.model.User;
import com.spring_boot_MVC.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Valid
public class UserController {

    public final UserService userService;
    public final UserDtoConverter userDtoConverter;

    public UserController(
            UserService userService,
            UserDtoConverter userDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateUserDto> getUser(
            @PathVariable Long id) {
        User user = userService.findUserById(id);
        CreateUserDto userDto = userDtoConverter.toUserDtoCreate(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<CreateUserDto> createUser(
            @RequestBody @Valid CreateUserDto userDto) {
        User saveUser = userService.addUser(userDtoConverter.toUserCreate(userDto));
        return ResponseEntity.ok(userDtoConverter.toUserDtoCreate(saveUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserDto> updateUser(
            @RequestBody @Valid UpdateUserDto updateUserDto,
            @PathVariable Long id) {
        User updateToUser = userService.updateUser(userDtoConverter.toUserUpdate(updateUserDto), id);
        return ResponseEntity.ok(userDtoConverter.toUserDtoUpdate(updateToUser));
    }
}
