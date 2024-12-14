package com.maiphong.quizapplication.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maiphong.quizapplication.entities.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, UUID> {
    Optional<QuizQuestion> findByQuizIdAndQuestionId(UUID quizId, UUID questionId);

    void deleteByQuizIdAndQuestionId(UUID quizId, UUID questionId);

}
