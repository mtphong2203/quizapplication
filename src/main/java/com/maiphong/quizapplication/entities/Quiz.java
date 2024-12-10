package com.maiphong.quizapplication.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "quizzes")
public class Quiz extends EntityMaster {

    @Column(columnDefinition = "NVARCHAR(255)", nullable = false)
    private String title;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String description;

    @Column(nullable = false)
    private double duration;

    @OneToMany(mappedBy = "quiz")
    private Set<QuizQuestion> quizQuestions;

    @OneToMany(mappedBy = "quiz")
    private Set<UserQuiz> userQuizs;

}
