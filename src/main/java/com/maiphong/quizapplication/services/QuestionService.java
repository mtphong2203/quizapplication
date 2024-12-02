package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.quizapplication.dtos.question.QuestionCreateEditDTO;
import com.maiphong.quizapplication.dtos.question.QuestionMasterDTO;
import com.maiphong.quizapplication.entities.QuestionType;

public interface QuestionService {
    List<QuestionMasterDTO> getAll();

    List<QuestionMasterDTO> searchByContent(String keyword);

    List<QuestionMasterDTO> searchByType(QuestionType type);

    Page<QuestionMasterDTO> searchPage(String keyword, Pageable pageable);

    QuestionMasterDTO getById(UUID id);

    QuestionMasterDTO create(QuestionCreateEditDTO questionDTO);

    QuestionMasterDTO update(UUID id, QuestionCreateEditDTO questionEditDTO);

    boolean delete(UUID id);
}
