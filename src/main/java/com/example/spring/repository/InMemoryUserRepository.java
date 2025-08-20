package com.example.spring.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.spring.model.Account;
import com.example.spring.model.User;

@Repository
public class InMemoryUserRepository {
    private List<User> usersList;

    public InMemoryUserRepository() {
        this.usersList = new ArrayList<User>();
    }

    public User addUser(Long id, String login) {
        User user = new User(login);
        usersList.add(user);
        return user;
    }

    public Account addAccount(User user, BigDecimal moneyAmount) {
        List<Account> accountList = user.getAccountList();
        Account account = new Account(user, moneyAmount);
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

    public void deleteAccountById(long id) {
        for (User user : usersList) {
            Optional<Account> accountOpt = user.getAccountList().stream()
                    .filter(a -> a.getId() == id)
                    .findFirst();

            if (accountOpt.isPresent()) {
                Account accountToDelete = accountOpt.get();
                List<Account> userAccounts = user.getAccountList();

                if (userAccounts.size() <= 1) {
                    throw new IllegalStateException("Cannot delete the last account of a user");
                }

                if (accountToDelete.getMoneyAmount().compareTo(BigDecimal.ZERO) > 0) {
                    Account existingAccount = userAccounts.stream()
                            .filter(a -> a.getId() != id)
                            .findFirst()
                            .orElseThrow();
                    BigDecimal balanceToTransfer = accountToDelete.getMoneyAmount();
                    existingAccount.setMoneyAmount(existingAccount.getMoneyAmount().add(balanceToTransfer));
                    accountToDelete.setMoneyAmount(BigDecimal.ZERO);
                }

                userAccounts.remove(accountOpt.get());
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
