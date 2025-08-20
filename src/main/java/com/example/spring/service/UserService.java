package com.example.spring.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.spring.model.Account;
import com.example.spring.model.User;
import com.example.spring.repository.AccountsRepository;
import com.example.spring.repository.InMemoryUserRepository;
import com.example.spring.repository.UsersRepository;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;
    private final long defaultAccountAmount;

    public UserService(UsersRepository usersRepository,
            AccountsRepository accountsRepository,
            @Value("${account.default-amount}") long defaultAccountAmount) {
        this.usersRepository = usersRepository;
        this.accountsRepository = accountsRepository;
        this.defaultAccountAmount = defaultAccountAmount;
    }

    public User createUser(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Login cannot be empty");
        }

        if (usersRepository.existsByLogin(login)) {
            throw new IllegalArgumentException(
                    "Think of another login, this login is already taken");
        }

        User user = new User(login);
        usersRepository.addUser(user);

        Account account = new Account(user,
                BigDecimal.valueOf(defaultAccountAmount));
        accountsRepository.addAccount(account);

        return user;
    }

    public User findByUserId(long id) {
        return usersRepository.findByUserId(id);
    }

    public List<User> getAllUsers() {
        return usersRepository.getAllUsers();
    }
}
