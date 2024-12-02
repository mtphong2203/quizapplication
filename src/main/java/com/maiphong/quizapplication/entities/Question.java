package com.maiphong.quizapplication.entities;

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
}
