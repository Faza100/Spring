package com.example.spring_core.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class User {
    private Long id;
    private String login;
    private List<Account> accountList;

    public User(Long id, String login) {
        this.id = id;
        this.login = login;
        this.accountList = new LinkedList<Account>();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
}
