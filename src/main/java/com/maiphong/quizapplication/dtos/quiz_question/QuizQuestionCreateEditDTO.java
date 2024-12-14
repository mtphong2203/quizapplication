package com.maiphong.quizapplication.dtos.quiz_question;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionCreateEditDTO {
    private UUID quizId;

    private UUID questionId;
}
