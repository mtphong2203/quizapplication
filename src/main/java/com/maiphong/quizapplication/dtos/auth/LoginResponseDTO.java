package com.maiphong.quizapplication.dtos.auth;

import com.maiphong.quizapplication.dtos.user.UserInformationDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;

    private UserInformationDTO userInformationDTO;
}
