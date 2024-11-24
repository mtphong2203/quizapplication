package com.maiphong.quizapplication.dtos.user;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDTO {
    private UUID id;

    @NotNull(message = "First name should not be null")
    private String firstName;

    @NotNull(message = "Last name should not be null")
    private String lastName;

    @NotNull(message = "Email should not be null")
    private String email;

    @NotNull(message = "Avatar should be appear")
    private String thumbnailUrl;

    @NotNull(message = "Password should not be null")
    private String password;
}
