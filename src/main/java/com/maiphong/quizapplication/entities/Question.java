package com.maiphong.quizapplication.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "questionType", nullable = false)
    private String questionType;

    @Column(name = "create_at", unique = true, nullable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at", unique = true)
    private LocalDateTime updateAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
