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

import com.spring_boot_MVC.converter.PetDtoConverter;
import com.spring_boot_MVC.dto.PetDto;
import com.spring_boot_MVC.model.Pet;
import com.spring_boot_MVC.service.PetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pets")
@Valid
public class PetController {

    public final PetService petService;
    public final PetDtoConverter petDtoConverter;

    public PetController(
            PetService petService,
            PetDtoConverter petDtoConverter) {
        this.petService = petService;
        this.petDtoConverter = petDtoConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(
            @PathVariable Long id) {
        Pet pet = petService.findPetById(id);
        return ResponseEntity.ok(petDtoConverter.toPetDto(pet));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeletePet(
            @PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PetDto> createPet(
            @RequestBody @Valid PetDto CreatePetDto,
            @PathVariable Long userId) {
        Pet updateToPet = petDtoConverter.toPet(CreatePetDto);
        updateToPet.setUserId(userId);
        petService.addPet(updateToPet, userId);
        return ResponseEntity.ok(petDtoConverter.toPetDto(updateToPet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(
            @PathVariable Long id,
            @RequestBody @Valid PetDto petDto) {
        Pet updateToPet = petDtoConverter.toPet(petDto);
        Pet updatedPet = petService.updatePet(updateToPet, id);
        return ResponseEntity.ok(petDtoConverter.toPetDto(updatedPet));
    }
}
