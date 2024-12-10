package com.maiphong.quizapplication.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer extends EntityMaster {

    @Column(columnDefinition = "NVARCHAR(50)", nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "answer")
    private Set<UserAnswer> userAnswers;
}
