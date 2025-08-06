package com.example.spring_core.repository;

import java.util.List;

import com.example.spring_core.model.User;

public class InMemoryUserRepository {
    private List<User> usersList;

    public void saveUser(User user) {
        usersList.add(user);
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}
