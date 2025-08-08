package com.example.spring_core.model;

import java.util.LinkedList;
import java.util.List;

public class User {
    private final Long id;
    private final String login;
    private List<Account> accountList;

    public User(Long id, String login) {
        this.id = id;
        this.login = login;
        this.accountList = new LinkedList<Account>();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return "User {id= " + id + ", login='" + login +
                "',\n/accountList=" + accountList;
    }
}
