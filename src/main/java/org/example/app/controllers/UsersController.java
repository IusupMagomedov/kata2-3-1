package org.example.app.controllers;

import org.example.app.models.User;
import org.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/create")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("email") String email,
                          @RequestParam("password") String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userService.createUser(user);
        return "redirect:/users";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("name") String name,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userService.updateUser(user);
        return "redirect:/users";
    }
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") String id) {
        Long longId = Long.valueOf(id);
        userService.deleteUser(longId);
        return "redirect:/users";
    }
}