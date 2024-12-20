package com.maiphong.quizapplication.dtos.role;

import com.maiphong.quizapplication.dtos.BaseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO extends BaseDTO {

    private String name;

    private String description;

}
