package com.maiphong.quizapplication.dtos.role;

import com.maiphong.quizapplication.dtos.MasterCreateEditDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class RoleCreateEditDTO extends MasterCreateEditDTO {

    @NotNull(message = "Name is not null")
    private String name;

    @NotNull(message = "Description is not null")
    private String description;
}
