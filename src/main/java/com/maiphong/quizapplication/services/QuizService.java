package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateEditDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizMasterDTO;
import com.maiphong.quizapplication.dtos.quiz_question.QuizQuestionCreateEditDTO;
import com.maiphong.quizapplication.dtos.quiz_question.QuizQuestionMasterDTO;

public interface QuizService {
    List<QuizMasterDTO> getAll();

    List<QuizMasterDTO> searchByTitle(String keyword);

    Page<QuizMasterDTO> search(String keyword, Pageable pageable);

    QuizMasterDTO getById(UUID id);

    QuizMasterDTO create(QuizCreateEditDTO quizDTO);

    QuizMasterDTO update(UUID id, QuizCreateEditDTO quizDTO);

    QuizQuestionMasterDTO addQuestionToQuiz(QuizQuestionCreateEditDTO masterDTO);

    boolean delete(UUID id);

    boolean deleteQuestionFromQuiz(UUID quizId, UUID questionId);

}
