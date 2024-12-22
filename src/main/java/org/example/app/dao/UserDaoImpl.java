package org.example.app.dao;

import org.example.app.models.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;


    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        System.out.println("findAll");
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

//        List<User> users = new ArrayList<>();
//        Random random = new Random();
//
//        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hank", "Ivy", "Jack"};
//        String[] domains = {"example.com", "test.com", "email.com", "mail.com"};
//
//        for (int i = 0; i < 10; i++) {
//            String name = names[random.nextInt(names.length)];
//            String password = "pass" + (1000 + random.nextInt(9000)); // Генерация пароля вида "pass1234"
//            String email = name.toLowerCase() + "@" + domains[random.nextInt(domains.length)];
//            users.add(new User(name, password, email));
//        }


    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Long save(User user) {
        entityManager.persist(user);
        return user.getId();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
