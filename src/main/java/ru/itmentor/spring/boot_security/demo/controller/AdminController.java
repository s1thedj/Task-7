package ru.itmentor.spring.boot_security.demo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;



import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.DTO.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AdminController(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UserDTO> users() {
        List<UserDTO> users = new ArrayList<>();
        for (User user : userService.findAll()) {
            users.add(convertToUserDTO(user));
        }
        return users;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            User user = convertToUser(userDTO);
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            Set<Role> roles = userDTO.getRoles().stream()
                    .map(roleName -> roleService.getRoleByName(roleName))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
            userService.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") long id) {
        userService.delete(id);
        return "Deleted user with id: " + id;
    }


    @PatchMapping("/edit")
    public String updateUser(@RequestBody UserDTO userDTO, @RequestParam(value = "id") long id) {
        User user = convertToUser(userDTO);
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleName -> roleService.getRoleByName(roleName))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user.setId(id);
        userService.create(user);
        return "Updated user with id: " + id;
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
