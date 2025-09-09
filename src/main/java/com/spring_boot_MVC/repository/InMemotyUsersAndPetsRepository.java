package com.spring_boot_MVC.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.spring_boot_MVC.model.Pet;
import com.spring_boot_MVC.model.User;

@Repository
public class InMemotyUsersAndPetsRepository {

    public static Long petCounter = 0L;
    public static Long userCounter = 0L;
    private List<User> usersList;

    public InMemotyUsersAndPetsRepository() {
        this.usersList = new ArrayList<User>();
    }

    public User addUser(User user) {
        generateUserCounter();
        user.setId(userCounter);
        usersList.add(user);
        return user;
    }

    public Pet addPet(Pet pet, User user) {
        generatePetCounter();
        pet.setId(petCounter);
        pet.setUserId(user.getId());
        List<Pet> petsList = user.getPets();
        petsList.add(pet);
        return pet;
    }

    public User updateUser(User updatedUser, Long userId) {
        for (int i = 0; i < usersList.size(); i++) {
            User currentUser = usersList.get(i);
            if (currentUser.getId() != null &&
                    currentUser.getId().equals(userId)) {
                usersList.set(i, updatedUser);
                return updatedUser;
            }
        }
        throw new IllegalArgumentException("User with id "
                + userId + " not found");
    }

    public Pet updatePet(Pet updatedPet, Long petId, User user) {
        for (int i = 0; i < user.getPets().size(); i++) {
            Pet currentPet = user.getPets().get(i);
            if (currentPet.getId() != null &&
                    currentPet.getId().equals(petId)) {
                user.getPets().set(i, updatedPet);
                return updatedPet;
            }
        }
        throw new IllegalArgumentException("Pet with id " +
                petId + " not found");
    }

    public User findUserById(long id) {
        return usersList.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such ID: " + id));
    }

    public Pet findPetById(long id) {
        return usersList.stream()
                .flatMap(u -> u.getPets().stream())
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no pet with such ID: " + id));
    }

    public void deleteUserById(long id) {
        Optional<User> accountOpt = usersList.stream()
                .filter(a -> a.getId() == id)
                .findFirst();

        if (accountOpt.isPresent()) {
            usersList.remove(accountOpt.get());
            return;
        }
        throw new IllegalArgumentException("There is no user with ID: " + id);
    }

    public void deletePetById(long id) {
        for (User user : usersList) {
            Optional<Pet> accountOpt = user.getPets().stream()
                    .filter(a -> a.getId() == id)
                    .findFirst();

            if (accountOpt.isPresent()) {
                user.getPets().remove(accountOpt.get());
                return;
            }
        }
        throw new IllegalArgumentException("There is no pet with ID: " + id);
    }

    public List<User> getUserList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    private Long generateUserCounter() {
        return userCounter++;
    }

    public Long getUserCounter() {
        return userCounter;
    }

    private Long generatePetCounter() {
        return petCounter++;
    }

    public Long getPetCounter() {
        return petCounter;
    }

}
