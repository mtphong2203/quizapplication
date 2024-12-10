package com.maiphong.quizapplication.entities;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuizQuestion extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "quiz_id", insertable = false, updatable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;

    @Column(name = "quiz_order")
    private int order;
}
