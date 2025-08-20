package com.example.spring.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.spring.model.Account;
import com.example.spring.model.User;
import com.example.spring.repository.AccountsRepository;
import com.example.spring.repository.UsersRepository;

@Service
public class AccountService {

    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;;
    private final BigDecimal transferCommission;

    public AccountService(UsersRepository usersRepository,
            AccountsRepository accountsRepository,
            @Value("${account.transfer-commission}") BigDecimal transferCommission) {
        this.usersRepository = usersRepository;
        this.accountsRepository = accountsRepository;
        this.transferCommission = transferCommission;
    }

    public Account createAccount(long userId) {
        User user = usersRepository.findByUserId(userId);
        Account account = new Account(user,
                BigDecimal.ZERO);
        accountsRepository.addAccount(account);
        return account;
    }

    public void accountDeposit(long userId, BigDecimal moneyAmount) {
        Account account = accountsRepository.findByAccountId(userId);
        if (account == null) {
            throw new IllegalArgumentException("There is no user with such ID: " + userId);
        }
        BigDecimal currentBalance = account.getMoneyAmount();
        account.setMoneyAmount(currentBalance.add(moneyAmount));
        accountsRepository.updateAccount(account);
    }

    public void amountWithdraw(long id, BigDecimal moneyAmount) {
        Account account = accountsRepository.findByAccountId(id);
        BigDecimal currentBalance = account.getMoneyAmount();
        if (currentBalance.compareTo(moneyAmount) < 0) {
            throw new IllegalArgumentException(
                    "Executing command ACCOUNT_WITHDRAW: error=No such money to withdraw\n" +
                            "/from account: id=" + id + ", moneyAmount="
                            + currentBalance + ", attemptedWithdraw=" + moneyAmount);
        }
        account.setMoneyAmount(currentBalance.subtract(moneyAmount));
        accountsRepository.updateAccount(account);
    }

    public void accountTrransfer(long sourceId, long targetId, BigDecimal moneyAmount) {
        Account accountSourceId = accountsRepository.findByAccountId(sourceId);
        Account accountTargetId = accountsRepository.findByAccountId(targetId);
        BigDecimal currentSourceBalance = accountSourceId.getMoneyAmount();
        BigDecimal currentTargeBalance = accountTargetId.getMoneyAmount();
        if (currentSourceBalance.compareTo(moneyAmount) < 0) {
            throw new IllegalArgumentException(
                    "The account source ID " + sourceId + " does not have enough money to transfer");
        }

        BigDecimal moneyCommission = moneyAmount.multiply(transferCommission);
        accountSourceId.setMoneyAmount(currentSourceBalance.subtract(moneyAmount));
        accountTargetId.setMoneyAmount(currentTargeBalance.add(moneyAmount.subtract(moneyCommission)));
        accountsRepository.updateAccount(accountSourceId);
        accountsRepository.updateAccount(accountTargetId);
    }

    public void deleteAccountById(long id) {
        Account account = accountsRepository.findByAccountId(id);
        if (account == null) {
            throw new IllegalArgumentException("Account with ID " + id + " not found");
        }

        Long userId = account.getUser().getId();
        List<Account> userAccounts = accountsRepository.accountsByUserId(userId);

        if (userAccounts.size() <= 1) {
            throw new IllegalStateException(
                    "Cannot delete the last account for user");
        }

        if (account.getMoneyAmount().compareTo(BigDecimal.ZERO) > 0) {
            Account existingAccount = userAccounts.stream()
                    .filter(a -> a.getId() != id)
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalStateException("No target account found"));
            BigDecimal balanceToTransfer = account.getMoneyAmount();
            existingAccount.setMoneyAmount(
                    existingAccount.getMoneyAmount().add(balanceToTransfer));
            account.setMoneyAmount(BigDecimal.ZERO);
            accountsRepository.updateAccount(account);
            accountsRepository.updateAccount(existingAccount);
        }

        accountsRepository.deleteAccountById(id);
    }
}
