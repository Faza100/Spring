package com.spring_boot_MVC.service;

import org.springframework.stereotype.Service;

import com.spring_boot_MVC.model.Pet;
import com.spring_boot_MVC.model.User;
import com.spring_boot_MVC.repository.InMemotyUsersAndPetsRepository;

@Service
public class PetService {

    public final InMemotyUsersAndPetsRepository repository;

    public PetService(InMemotyUsersAndPetsRepository repository) {
        this.repository = repository;
    }

    public Pet findPetById(Long id) {
        return repository.findPetById(id);
    }

    public void deletePetById(Long id) {
        repository.deletePetById(id);
    }

    public Pet addPet(Pet pet, Long userId) {
        User user = repository.findUserById(userId);
        return repository.addPet(pet, user);
    }

    public Pet updatePet(Pet pet, Long petId) {

        Pet existingPet = repository.findPetById(petId);

        User user = repository.findUserById(existingPet.getUserId());
        existingPet.setUserId(pet.getId());
        existingPet.setName(pet.getName());
        existingPet.setUserId(pet.getUserId());
        repository.updatePet(existingPet, petId, user);

        return existingPet;
    }
}
