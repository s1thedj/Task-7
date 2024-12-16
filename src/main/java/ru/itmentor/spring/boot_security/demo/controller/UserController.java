package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String userInfo(@PathVariable("id") long id, Model model, Authentication authentication) {
        authentication.getPrincipal();
        System.out.println("Received ID: " + id);
        if (authentication.getPrincipal() instanceof User user) {
            if (id == user.getId()) {
                user = userService.findById(id).orElse(null);
                model.addAttribute("user", user);
                return "user";
            }
        }
        return "redirect:/";
    }
}