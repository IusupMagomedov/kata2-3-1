package org.example.app.controllers;

import org.example.app.dao.UserDao;
import org.example.app.dao.UserDaoImpl;
import org.example.app.models.User;
import org.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(@RequestParam(value = "limit", required = false)
                           Integer limit, Model model) {
        List<User> users = userService.getUsers(limit);
        model.addAttribute("users", users);
        return "users";
    }
}