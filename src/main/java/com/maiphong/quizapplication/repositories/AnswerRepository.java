package com.maiphong.quizapplication.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maiphong.quizapplication.entities.Answer;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {

}
