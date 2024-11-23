package com.maiphong.quizapplication.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maiphong.quizapplication.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

}
