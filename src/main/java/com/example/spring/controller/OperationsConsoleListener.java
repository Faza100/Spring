package com.example.spring.controller;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.example.spring.model.User;
import com.example.spring.service.AccountService;
import com.example.spring.service.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class OperationsConsoleListener {

    private final AccountService accountService;
    private final UserService userService;

    private Scanner scanner;

    public OperationsConsoleListener(AccountService accountService, UserService userService) {
        this.scanner = new Scanner(System.in);
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostConstruct
    public void ConsoleListener() {
        while (true) {
            printMenu();
            handleCommand(scanner.nextLine());
        }
    }

    public void handleCommand(String command) {
        try {
            switch (command) {
                case "1":
                    if (searcUsers())
                        return;
                    createAccount();
                    break;
                case "2":
                    showAllUsers();
                    break;
                case "3":
                    if (searcUsers())
                        return;
                    accountClose();
                    break;
                case "4":
                    if (searcUsers())
                        return;
                    accountWithdraw();
                    break;
                case "5":
                    if (searcUsers())
                        return;
                    accouuntDeposit();
                    break;
                case "6":
                    if (searcUsers())
                        return;
                    accountTrransfer();
                    break;
                case "7":
                    createUser();
                    break;
                default:
                    System.out.println("Select a command from the list");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Enter a number.");
            scanner.nextLine();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
        }
    }

    public boolean searcUsers() {
        if (userService.getAllUsers().isEmpty()) {
            System.out.println("First, create a user!");
            return true;
        }
        return false;
    }

    public void createAccount() {
        System.out.println("Enter the user id for which to create an account: ");
        long userId = scanner.nextLong();
        scanner.nextLine();
        accountService.createAccount(userId);
        System.out.println("New account created with ID: " + userId +
                " for user:" + userService.findByUserId(userId).getLogin());
    }

    public void showAllUsers() {
        System.out.println(userService.getAllUsers());
    }

    public void accountClose() {
        System.out.println("Enter account ID to close: ");
        long accountId = scanner.nextLong();
        scanner.nextLine();
        accountService.deleteAccountById(accountId);
        System.out.println("Account with ID " +
                accountId + " has been closed");
    }

    public void accountWithdraw() {
        System.out.println("Enter account ID to withdraw from: ");
        long accountId = scanner.nextLong();
        System.out.println("Enter amount to withdraw: ");
        BigDecimal moneyAmount = scanner.nextBigDecimal();
        scanner.nextLine();
        accountService.amountWithdraw(accountId, moneyAmount);
        System.out.println("Amount " + moneyAmount +
                " withdraw to account ID: " + accountId);
    }

    public void accouuntDeposit() {
        System.out.println("Enter account ID: ");
        long accountId = scanner.nextLong();
        System.out.println("Enter amount to deposit: ");
        BigDecimal moneyAmount = scanner.nextBigDecimal();
        scanner.nextLine();
        accountService.accountDeposit(accountId, moneyAmount);
        System.out.println("Amount " + moneyAmount +
                " deposited to account ID: " + accountId);

    }

    public void accountTrransfer() {
        System.out.println("Enter source account ID: ");
        long accountSourceId = scanner.nextLong();
        System.out.println("Enter target account ID: ");
        long accountTargetId = scanner.nextLong();
        System.out.println("Enter amount to transfer: ");
        BigDecimal moneyAmount = scanner.nextBigDecimal();
        scanner.nextLine();
        accountService.accountTrransfer(accountSourceId, accountTargetId, moneyAmount);
        System.out.println("Amount " + moneyAmount
                + " transferred from account ID " + accountSourceId +
                " to account ID " + accountTargetId);

    }

    public void createUser() {
        System.out.println("Enter login for new user:");
        String setLogin = scanner.nextLine();
        User user = userService.createUser(setLogin);
        System.out.println(user.toString());
    }

    public void printMenu() {
        System.out.println("Please enter one of the transaction types using the number:\n" +
                "1.ACCOUNT_CREATE\n" +
                "2.SHOW_ALL_USERS\n" +
                "3.ACCOUNT_CLOSE\n" +
                "4.ACCOUNT_WITHDRAW\n" +
                "5.ACCOUNT_DEPOSIT\n" +
                "6.ACCOUNT_TRANSFER\n" +
                "7.USER_CREATE");
    }
}
