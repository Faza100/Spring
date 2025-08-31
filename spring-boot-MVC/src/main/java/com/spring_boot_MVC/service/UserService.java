package com.spring_boot_MVC.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring_boot_MVC.model.Pet;
import com.spring_boot_MVC.model.User;
import com.spring_boot_MVC.repository.InMemotyUsersAndPetsRepository;

@Service
public class UserService {

    public final InMemotyUsersAndPetsRepository repository;

    public UserService(
            InMemotyUsersAndPetsRepository repository,
            PetService petService) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.getUserList();
    }

    public User findUserById(Long id) {
        return repository.findUserById(id);
    }

    public void deleteUserById(Long id) {
        repository.deleteUserById(id);
    }

    public User updateUser(User user, Long userId) {

        User existingUser = repository.findUserById(userId);

        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getAge() != null) {
            existingUser.setAge(user.getAge());
        }
        if (user.getPets() != null && !user.getPets().isEmpty()) {
            existingUser.setPets(user.getPets());
        }

        return repository.updateUser(existingUser, userId);
    }

    public User addUser(User user) {
        User userWithoutPets = new User(
                null,
                user.getName(),
                user.getEmail(),
                user.getAge(),
                new ArrayList<>());

        User savedUser = repository.addUser(userWithoutPets);
        if (user.getPets() != null) {
            List<Pet> petsCopy = new ArrayList<>(user.getPets());
            for (Pet pet : petsCopy) {
                repository.addPet(pet, savedUser);
            }
        }
        return savedUser;
    }
}
