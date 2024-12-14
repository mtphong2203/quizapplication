package com.maiphong.quizapplication.dtos.quiz_question;

import java.util.UUID;

import com.maiphong.quizapplication.dtos.MasterDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionMasterDTO extends MasterDTO {
    private UUID quizId;

    private UUID questionId;
}
