package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizEditDTO;

public interface QuizService {
    List<QuizDTO> getAll();

    Page<QuizDTO> search(String keyword, Pageable pageable);

    QuizDTO getById(UUID id);

    boolean create(QuizCreateDTO quizCreateDTO);

    boolean update(QuizEditDTO quizEditDTO);

    boolean delete(UUID id);

}
