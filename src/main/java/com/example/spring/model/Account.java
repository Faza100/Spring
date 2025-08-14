package com.example.spring.model;

import java.math.BigDecimal;

public class Account {
    private final long id;
    private final long userId;
    private BigDecimal moneyAmount;

    public Account(long id, long userId, BigDecimal moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account {id= " + id + ", userId='" + userId +
                "',\n/moneyAmount=" + moneyAmount;
    }
}
