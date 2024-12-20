package com.maiphong.quizapplication.dtos.user;

import com.maiphong.quizapplication.dtos.BaseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {
    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phoneNumber;

    private String thumbnailUrl;

}
