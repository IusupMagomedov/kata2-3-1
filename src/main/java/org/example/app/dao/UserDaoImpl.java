package org.example.app.dao;


import org.example.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Random random = new Random();

        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hank", "Ivy", "Jack"};
        String[] domains = {"example.com", "test.com", "email.com", "mail.com"};

        for (int i = 0; i < 10; i++) {
            String name = names[random.nextInt(names.length)];
            String password = "pass" + (1000 + random.nextInt(9000)); // Генерация пароля вида "pass1234"
            String email = name.toLowerCase() + "@" + domains[random.nextInt(domains.length)];
            users.add(new User(name, password, email));
        }

        return users;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public void save(User user) {
        EntityManager entityManager;
        EntityTransaction transaction;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            entityManager.persist(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
