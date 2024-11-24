package com.maiphong.quizapplication.dtos.role;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEditDTO {
    private UUID id;

    @NotNull(message = "Name is not null")
    private String name;

    @NotNull(message = "Description is not null")
    private String description;

    @NotNull(message = "Active is not null")
    private boolean isActive;
}
