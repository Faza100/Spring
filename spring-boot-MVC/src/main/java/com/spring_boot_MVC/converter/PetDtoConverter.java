package com.spring_boot_MVC.converter;

import org.springframework.stereotype.Component;

import com.spring_boot_MVC.dto.PetDto;
import com.spring_boot_MVC.model.Pet;

@Component
public class PetDtoConverter {

    public Pet toPet(PetDto petDto) {
        return new Pet(
                petDto.getId(),
                petDto.getName(),
                petDto.getUserId());
    }

    public PetDto toPetDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getId());
    }
}
