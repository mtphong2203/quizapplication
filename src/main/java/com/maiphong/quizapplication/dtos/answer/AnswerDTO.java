package com.maiphong.quizapplication.dtos.answer;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

    @NotNull(message = "Content is not null")
    private String content;

    @NotNull(message = "Correct answer is not null")
    private boolean isCorrect;
}
