package com.maiphong.quizapplication.entities;

import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class QuizQuestionId {
    private UUID questionId;
    private UUID quizId;
}
