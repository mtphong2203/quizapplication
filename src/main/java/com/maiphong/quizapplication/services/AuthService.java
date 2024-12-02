package com.maiphong.quizapplication.services;

import java.util.UUID;

import com.maiphong.quizapplication.dtos.auth.RegisterRequestDTO;

public interface AuthService {
    boolean existByUsername(String username);

    UUID register(RegisterRequestDTO requestDTO);
}
