package com.maiphong.quizapplication.dtos.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @NotNull(message = "First name should not be null")
    private String firstName;

    @NotNull(message = "Last name should not be null")
    private String lastName;

    @NotNull(message = "User name should not be null")
    @Length(max = 20, message = "Maximum is 20 characters")
    private String username;

    @NotNull(message = "Email should not be nulll")
    private String email;

    @NotNull(message = "Password should not be null")
    private String password;

}
