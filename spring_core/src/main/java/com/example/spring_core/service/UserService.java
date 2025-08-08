package com.example.spring_core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.example.spring_core.model.User;
import com.example.spring_core.repository.InMemoryUserRepository;

@Service
public class UserService {

    private InMemoryUserRepository repository;
    private AccountService accountService;
    private final long defaultAccountAmount;
    private static Long userCounter = 0L;

    public UserService(InMemoryUserRepository repository,
            AccountService accountService,
            @Value("${account.default-amount}") long defaultAccountAmount) {
        this.repository = repository;
        this.accountService = accountService;
        this.defaultAccountAmount = defaultAccountAmount;
    }

    public User createUser(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be empty");
        }

        if (repository.existsByLogin(login)) {
            throw new IllegalArgumentException("Think of another login, this login is already taken");
        }

        genereteUserCounter();
        accountService.genereteAccountCounter();
        User user = repository.addUser(getUserCounter(), login);
        repository.addAccount(accountService.getAccountCounter(), getUserCounter(),
                BigDecimal.valueOf(defaultAccountAmount), user);
        return user;
    }

    public User findByUserId(long id) {
        return repository.findByUserId(id);
    }

    public List<User> getUserList() {
        return repository.getUserList();
    }

    public Long getUserCounter() {
        return userCounter;
    }

    public void genereteUserCounter() {
        userCounter++;
    }
}
