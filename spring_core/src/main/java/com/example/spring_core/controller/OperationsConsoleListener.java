package com.example.spring_core.controller;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class OperationsConsoleListener {

    private Scanner scanner;

    public OperationsConsoleListener() {
        this.scanner = new Scanner(System.in);
    }

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
                    if (userService.getUserList().isEmpty()) {
                        System.out.println("First, create a user!");
                    } else {
                        createAccount();
                    }
                    break;
                case "2":
                    showAllUsers();
                    break;
                case "3":
                    if (userService.getUserList().isEmpty()) {
                        System.out.println("First, create a user!");
                    } else {
                        accountClose();
                    }
                    break;
                case "4":
                    if (userService.getUserList().isEmpty()) {
                        System.out.println("First, create a user!");
                    } else {
                        accountWithdraw();
                    }
                    break;
                case "5":
                    if (userService.getUserList().isEmpty()) {
                        System.out.println("First, create a user!");
                    } else {
                        accouuntDeposit();
                    }
                    break;
                case "6":
                    if (userService.getUserList().isEmpty()) {
                        System.out.println("First, create a user!");
                    } else {
                        accountTrransfer();
                    }
                    break;
                case "7":
                    createUser();
                    break;
                default:
                    System.out.println("Select a command from the list");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Enter a number.");
            scanner.nextLine(); // Очистка буфера
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
        }
    }

    public void createAccount() {

    }

    public void showAllUsers() {

    }

    public void accountClose() {

    }

    public void accountWithdraw() {

    }

    public void accouuntDeposit() {

    }

    public void accountTrransfer() {

    }

    public void createUser() {

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
