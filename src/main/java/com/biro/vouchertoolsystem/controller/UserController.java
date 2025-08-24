package com.biro.vouchertoolsystem.controller;

import com.biro.vouchertoolsystem.Dtos.Request.UserRequestDTO;
import com.biro.vouchertoolsystem.model.User;
import com.biro.vouchertoolsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User newUser(@RequestBody UserRequestDTO  userRequestDTO) {
        return userService.registerUser(userRequestDTO);
    }

    @PostMapping("/login")
    public String  login(String name, String password) {
        return userService.verifyUser(name, password);
    }
}
