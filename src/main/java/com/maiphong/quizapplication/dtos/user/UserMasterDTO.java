package com.maiphong.quizapplication.dtos.user;

import java.util.Set;

import com.maiphong.quizapplication.dtos.MasterDTO;
import com.maiphong.quizapplication.entities.Role;

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

    private Set<String> role;

}
