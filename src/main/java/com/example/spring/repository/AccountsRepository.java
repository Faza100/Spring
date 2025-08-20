package com.example.spring.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.example.spring.model.Account;

@Repository
public class AccountsRepository {

    public final SessionFactory sessionFactory;

    public AccountsRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Account findByAccountId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Account.class, id);
        }
    }

    public void updateAccount(Account account) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            try {
                session.merge(account);
                tr.commit();
            } catch (Exception e) {
                tr.rollback();
                throw new RuntimeException("Failed to update account balance", e);
            }
        }
    }

    public void deleteAccountById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            try {
                Account account = session.get(Account.class, id);
                session.remove(account);
                tr.commit();
            } catch (Exception e) {
                if (tr != null && tr.isActive()) {
                    tr.rollback();
                }
            }
        }
    }

    public List<Account> accountsByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Account a WHERE a.user.id = :userId", Account.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

    public void addAccount(Account account) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            try {
                session.persist(account);
                tr.commit();
            } catch (Exception e) {
                tr.rollback();
            }
        }
    }
}
