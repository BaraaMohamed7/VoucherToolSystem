package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Request.LoginUserDTO;
import com.biro.vouchertoolsystem.Dtos.Request.UserRequestDTO;
import com.biro.vouchertoolsystem.model.User;
import com.biro.vouchertoolsystem.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager  authenticationManager;
    JWTService  jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User registerUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        String plainPassword = userRequestDTO.getPassword();
        user.setPassword(encoder.encode(plainPassword));
        return userRepository.save(user);
    }

    public String verifyUser(LoginUserDTO loginUserDTO, HttpServletResponse response) {
        System.out.println("Login attempt for user: " + loginUserDTO.getName());

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserDTO.getName(),
                            loginUserDTO.getPassword()
                    )
            );
            if (auth.isAuthenticated()) {
                response.addHeader("Authorization", "Bearer " + jwtService.generateToken(loginUserDTO.getName()));
                return "Authentication successful";
            } else {
                return "Authentication failed";
            }
    }

}
