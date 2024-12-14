package com.maiphong.quizapplication.dtos.quiz;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrepareQuiz {
    private UUID userId;

    private UUID quizId;

    private String quizCode;
}
