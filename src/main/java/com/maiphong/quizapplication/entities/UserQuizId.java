package com.maiphong.quizapplication.entities;

import java.util.UUID;

import jakarta.persistence.Embeddable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserQuizId {

    private UUID quizId;
    private UUID userId;
}
