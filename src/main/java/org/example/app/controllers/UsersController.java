package org.example.app.controllers;

import org.example.app.dao.UserDao;
import org.example.app.dao.UserDaoImpl;
import org.example.app.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/users")
public class UsersController {
    @GetMapping
    public String getUsers(Model model) {
        UserDao userDao = new UserDaoImpl();
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}