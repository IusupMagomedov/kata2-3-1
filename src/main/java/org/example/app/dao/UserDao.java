package org.example.app.dao;

import java.util.List;
import org.example.app.models.User;

public interface UserDao {
    List<User> findAll();
    User findById(int id);
    void save(User user);
    void update(User user);
    void delete(User user);
}
