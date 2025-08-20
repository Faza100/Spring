package com.example.spring.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.example.spring.model.User;

@Repository
public class UsersRepository {

    public final SessionFactory sessionFactory;

    public UsersRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.accountList",
                    User.class).list();
        }
    }

    public void addUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            try {
                session.persist(user);
                tr.commit();
            } catch (Exception e) {
                tr.rollback();
            }
        }
    }

    public User findByUserId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    public boolean existsByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery(
                    "SELECT u FROM User u WHERE u.login = :login",
                    User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            return user != null;
        }
    }
}
