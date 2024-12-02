package com.maiphong.quizapplication.dtos.role;

import com.maiphong.quizapplication.dtos.MasterDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleMasterDTO extends MasterDTO {

    private String name;

    private String description;

}
