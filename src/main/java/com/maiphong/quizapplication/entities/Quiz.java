package com.maiphong.quizapplication.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "duration", nullable = false)
    private double duration;

    @Column(name = "create_at", unique = true, nullable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at", unique = true)
    private LocalDateTime updateAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "quiz")
    private List<QuizQuestion> quizzes;

    @OneToMany(mappedBy = "quiz")
    private List<UserQuiz> quizUsers;

}
