package com.biro.vouchertoolsystem.controller;

import com.biro.vouchertoolsystem.Dtos.Request.LoginUserDTO;
import com.biro.vouchertoolsystem.Dtos.Request.UserRequestDTO;
import com.biro.vouchertoolsystem.model.User;
import com.biro.vouchertoolsystem.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    public String  login(@RequestBody LoginUserDTO loginUserDTO, HttpServletResponse response) {
        return userService.verifyUser(loginUserDTO, response);
    }
}
