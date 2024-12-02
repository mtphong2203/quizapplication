package com.maiphong.quizapplication.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterCreateEditDTO {
    @NotNull(message = "Active is required")
    private boolean isActive;
}
