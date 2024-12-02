package com.maiphong.quizapplication.dtos.question;

import com.maiphong.quizapplication.dtos.MasterCreateEditDTO;
import com.maiphong.quizapplication.entities.QuestionType;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateEditDTO extends MasterCreateEditDTO {

    @NotNull(message = "Content should not be null")
    private String content;

    @NotNull(message = "Question type should be appear")
    private QuestionType type;
}
