package com.maiphong.quizapplication.dtos.user;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInformationDTO {
    private String firstName;

    private String lastName;

    private String displayName;

    private String username;

    private String email;

    private String phoneNumber;

    private String thumbnailUrl;

    private Set<String> roles;

}
