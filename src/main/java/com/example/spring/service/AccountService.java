package com.example.spring.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.spring.model.Account;
import com.example.spring.model.User;
import com.example.spring.repository.InMemoryUserRepository;

@Service
public class AccountService {
    private InMemoryUserRepository repository;
    private static Long accountCounter = 0L;
    private final BigDecimal transferCommission;

    public AccountService(InMemoryUserRepository repository,
            @Value("${account.transfer-commission}") BigDecimal transferCommission) {
        this.repository = repository;
        this.transferCommission = transferCommission;
    }

    public Account createAccount(long userId) {
        genereteAccountCounter();
        User user = repository.findByUserId(userId);
        Account account = repository.addAccount(getAccountCounter(), userId,
                BigDecimal.valueOf(0), user);
        return account;
    }

    public void accountDeposit(long userId, BigDecimal moneyAmount) {
        Account account = repository.findByAccountId(userId);
        BigDecimal currentBalance = account.getMoneyAmount();
        account.setMoneyAmount(currentBalance.add(moneyAmount));
    }

    public void amountWithdraw(long id, BigDecimal moneyAmount) {
        Account account = repository.findByAccountId(id);
        BigDecimal currentBalance = account.getMoneyAmount();
        if (currentBalance.compareTo(moneyAmount) < 0) {
            throw new IllegalArgumentException(
                    "Executing command ACCOUNT_WITHDRAW: error=No such money to withdraw\n" +
                            "/from account: id=" + id + ", moneyAmount="
                            + currentBalance + ", attemptedWithdraw=" + moneyAmount);
        }
        account.setMoneyAmount(currentBalance.subtract(moneyAmount));
    }

    public void accountTrransfer(long sourceId, long targetId, BigDecimal moneyAmount) {
        Account accountSourceId = repository.findByAccountId(sourceId);
        Account accountTargetId = repository.findByAccountId(targetId);
        BigDecimal currentSourceBalance = accountSourceId.getMoneyAmount();
        BigDecimal currentTargeBalance = accountTargetId.getMoneyAmount();
        if (currentSourceBalance.compareTo(moneyAmount) < 0) {
            throw new IllegalArgumentException(
                    "The account source ID " + sourceId + " does not have enough money to transfer");
        }
        BigDecimal moneyCommission = moneyAmount.multiply(transferCommission);
        accountSourceId.setMoneyAmount(currentSourceBalance.subtract(moneyAmount));
        accountTargetId.setMoneyAmount(currentTargeBalance.add(moneyAmount.subtract(moneyCommission)));
    }

    public void deliteAccountById(long id) {
        repository.deliteAccountById(id);
    }

    public Long getAccountCounter() {
        return accountCounter;
    }

    public void genereteAccountCounter() {
        accountCounter++;
    }
}
