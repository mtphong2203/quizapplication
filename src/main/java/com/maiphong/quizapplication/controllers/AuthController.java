package com.maiphong.quizapplication.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maiphong.quizapplication.dtos.auth.LoginRequestDTO;
import com.maiphong.quizapplication.dtos.auth.LoginResponseDTO;
import com.maiphong.quizapplication.dtos.user.UserCreateDTO;
import com.maiphong.quizapplication.services.AuthService;
import com.maiphong.quizapplication.services.TokenService;
import com.maiphong.quizapplication.services.UserService;

@RestController
@RequestMapping("api/manager/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenService tokenService;

    public AuthController(UserService userService, AuthService authService,
            AuthenticationManagerBuilder authenticationManagerBuilder, TokenService tokenService) {
        this.userService = userService;
        this.authService = authService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenService.generateAccessToken(authentication);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(accessToken);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody UserCreateDTO userCreateDTO) {
        if (authService.existByUsername(userCreateDTO.getUsername())) {
            return ResponseEntity.badRequest().body(false);
        }

        boolean isCreated = userService.create(userCreateDTO);

        return ResponseEntity.ok(isCreated);

    }

}
