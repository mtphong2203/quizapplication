package com.maiphong.quizapplication.entities;

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
}
