package org.example.app.service;

import java.util.List;
import org.example.app.models.User;

public interface UserService {
    List<User> getUsers();
    List<User> getUsers(Integer limit);
    User getUser(Long id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
}
