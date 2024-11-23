package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizEditDTO;

public interface QuizService {
    List<QuizDTO> getAll();

    QuizDTO getById(UUID id);

    boolean create(QuizCreateDTO quizCreateDTO);

    boolean update(QuizEditDTO quizEditDTO);

    boolean delete(UUID id);

}
