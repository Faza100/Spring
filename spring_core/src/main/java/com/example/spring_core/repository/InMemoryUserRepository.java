package com.example.spring_core.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.spring_core.model.Account;
import com.example.spring_core.model.User;

@Repository
public class InMemoryUserRepository {
    private List<User> usersList;

    public InMemoryUserRepository() {
        this.usersList = new ArrayList<User>();
    }

    public User addUser(Long id, String login) {
        User user = new User(id, login);
        usersList.add(user);
        return user;
    }

    public Account addAccount(long accountId, long userId, BigDecimal moneyAmount, User user) {
        List<Account> accountList = user.getAccountList();
        Account account = new Account(accountId, userId, moneyAmount);
        accountList.add(account);
        return account;
    }

    public boolean existsByLogin(String login) {
        return usersList.stream()
                .anyMatch(u -> u.getLogin().equals(login));
    }

    public User findByUserId(long id) {
        return usersList.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such ID: " + id));
    }

    public Account findByAccountId(long id) {
        return usersList.stream()
                .flatMap(u -> u.getAccountList().stream())
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no account with such ID: " + id));
    }

    public void deliteAccountById(long id) {
        for (User user : usersList) {
            Optional<Account> accountOpt = user.getAccountList().stream()
                    .filter(a -> a.getId() == id)
                    .findFirst();

            if (accountOpt.isPresent()) {
                user.getAccountList().remove(accountOpt.get());
                return;
            }
        }
        throw new IllegalArgumentException("There is no account with ID: " + id);

    }

    public List<User> getUserList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}
