package com.example.spring_core.model;

public class Account {
    private int id;
    private User user;
    private double moneyAmount;

    public Account(int id, User user, double moneyAmount) {
        this.id = id;
        this.user = user;
        this.moneyAmount = moneyAmount;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Long getUserId() {
        return user.getId();
    }
}
