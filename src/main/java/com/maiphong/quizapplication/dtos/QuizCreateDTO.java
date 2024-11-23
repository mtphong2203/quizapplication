package com.maiphong.quizapplication.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizCreateDTO {
    @NotNull(message = "Title should not be null")
    private String title;

    @Length(max = 255, message = "Maximum is 255 characters")
    private String description;

    @PositiveOrZero(message = "Should equal or greater than zero")
    private double duration;

    @NotNull(message = "Active should be appear!")
    private boolean isActive;
}
