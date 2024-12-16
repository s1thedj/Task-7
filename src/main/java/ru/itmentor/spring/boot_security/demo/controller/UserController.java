package ru.itmentor.spring.boot_security.demo.controller;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.DTO.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.util.UserErrorResponse;
import ru.itmentor.spring.boot_security.demo.util.UserNotFoundException;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/user")

public class UserController {

    private final ModelMapper modelMapper;

    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("")
    public UserDTO userInfo(@RequestParam(value = "id") long id, Authentication authentication) throws AccessDeniedException {
        authentication.getPrincipal();
        System.out.println("Received ID: " + id);
        if (authentication.getPrincipal() instanceof User user) {
            if (id == user.getId()) {
                user = userService.findById(id).orElseThrow(UserNotFoundException::new);
                return convertToUserDTO(user);
            }
        }
        throw new AccessDeniedException("Access Denied");
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException exception) {
        UserErrorResponse response = new UserErrorResponse("User with this id was not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Data
    public static class UserRequest {
        private long id;
    }
}