package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import com.maiphong.quizapplication.dtos.question.QuestionCreateDTO;
import com.maiphong.quizapplication.dtos.question.QuestionDTO;
import com.maiphong.quizapplication.dtos.question.QuestionEditDTO;

public interface QuestionService {
    List<QuestionDTO> getAll();

    QuestionDTO getById(UUID id);

    boolean create(QuestionCreateDTO questionCreateDTO);

    boolean update(QuestionEditDTO questionEditDTO);

    boolean delete(UUID id);
}
