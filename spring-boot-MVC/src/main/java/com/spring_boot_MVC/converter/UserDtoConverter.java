package com.spring_boot_MVC.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring_boot_MVC.dto.PetDto;
import com.spring_boot_MVC.dto.UpdateUserDto;
import com.spring_boot_MVC.dto.CreateUserDto;
import com.spring_boot_MVC.model.Pet;
import com.spring_boot_MVC.model.User;

@Component
public class UserDtoConverter {

    public final PetDtoConverter petDtoConverter;

    public UserDtoConverter(PetDtoConverter petDtoConverter) {
        this.petDtoConverter = petDtoConverter;
    }

    public User toUserCreate(CreateUserDto userDto) {

        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge(),
                convertPetsToEntity(userDto.getPets()));
    }

    public CreateUserDto toUserDtoCreate(User user) {

        return new CreateUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                convertPetsToDto(user.getPets()));
    }

    public UpdateUserDto toUserDtoUpdate(User user) {

        return new UpdateUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                convertPetsToDto(user.getPets()));
    }

    public User toUserUpdate(UpdateUserDto userDto) {

        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge(),
                convertPetsToEntity(userDto.getPets()));
    }

    private List<PetDto> convertPetsToDto(List<Pet> pets) {
        return pets.stream()
                .map(petDtoConverter::toPetDto)
                .toList();
    }

    private List<Pet> convertPetsToEntity(List<PetDto> petsDto) {
        return petsDto.stream()
                .map(petDtoConverter::toPet)
                .toList();
    }

}
