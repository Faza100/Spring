package com.example.my_progect.service.converter;

import org.springframework.stereotype.Service;

import com.example.my_progect.dto.user.UserRequestDto;
import com.example.my_progect.dto.user.UserResponseDto;
import com.example.my_progect.model.User;

@Service
public class UserConverter {

    public User toEntity(UserRequestDto useDto) {
        return new User(
                useDto.getLogin(),
                useDto.getAge(),
                useDto.getPassword());
    }

    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getAge(),
                user.getRole());
    }

}
