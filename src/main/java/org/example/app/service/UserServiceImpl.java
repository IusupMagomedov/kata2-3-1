package org.example.app.service;

import org.example.app.dao.UserDao;
import org.example.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> getUsers(Integer limit) {
        List<User> users = getUsers();
        if (limit == null || limit > users.size() || limit <= 0) {
            limit = users.size();
        }
        List<User> resultUsers = new ArrayList<User>();
        for (int i = 0; i < limit; i++) {
            resultUsers.add(users.get(i));
        }
        return resultUsers;
    }

    @Override
    public User getUser(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    @Override
    public Long createUser(User user) {
        return userDao.save(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
