package com.maiphong.quizapplication.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question extends EntityMaster {

    @Column(columnDefinition = "NVARCHAR(255)", nullable = false)
    private String content;

    @Column(nullable = false)
    private QuestionType type;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    @OneToMany(mappedBy = "question")
    private Set<QuizQuestion> quizQuestions;

    @OneToMany(mappedBy = "question")
    private Set<UserAnswer> userAnswers;
}
