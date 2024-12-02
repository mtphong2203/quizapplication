package com.maiphong.quizapplication.dtos.user;

import com.maiphong.quizapplication.dtos.MasterDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMasterDTO extends MasterDTO {
    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phoneNumber;

    private String thumbnailUrl;

}
