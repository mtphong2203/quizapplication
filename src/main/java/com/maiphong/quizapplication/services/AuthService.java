package com.maiphong.quizapplication.services;

import java.util.UUID;

import com.maiphong.quizapplication.dtos.auth.RegisterRequestDTO;
import com.maiphong.quizapplication.dtos.user.UserInformationDTO;

public interface AuthService {
    boolean existByUsername(String username);

    UUID register(RegisterRequestDTO requestDTO);

    UserInformationDTO getUserInformation(String username);
}
