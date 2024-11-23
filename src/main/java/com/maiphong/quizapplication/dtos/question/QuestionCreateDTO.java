package com.maiphong.quizapplication.dtos.question;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateDTO {
    @NotNull(message = "Content should not be null")
    private String content;

    @NotNull(message = "Question type should be appear")
    private String questionType;

    @NotNull(message = "Active should not be null")
    private boolean isActive;
}
