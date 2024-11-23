package com.maiphong.quizapplication.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizEditDTO;
import com.maiphong.quizapplication.entities.Quiz;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.repositories.QuizRepository;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public List<QuizDTO> getAll() {
        List<Quiz> quizzes = quizRepository.findAll();
        // convert dto to show view
        List<QuizDTO> quizDTOs = quizzes.stream().map(quiz -> {
            QuizDTO quizDTO = new QuizDTO();
            quizDTO.setId(quiz.getId());
            quizDTO.setTitle(quiz.getTitle());
            quizDTO.setDescription(quiz.getDescription());
            quizDTO.setDuration(quiz.getDuration());
            quizDTO.setActive(quiz.isActive());

            return quizDTO;
        }).toList();

        return quizDTOs;
    }

    @Override
    public QuizDTO getById(UUID id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quiz.getTitle());
        quizDTO.setDescription(quiz.getDescription());
        quizDTO.setDuration(quiz.getDuration());
        quizDTO.setActive(quiz.isActive());

        return quizDTO;

    }

    @Override
    public boolean create(QuizCreateDTO quizCreateDTO) {
        if (quizCreateDTO == null) {
            throw new IllegalArgumentException("Quiz create should not empty");
        }

        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(quizCreateDTO.getTitle());
        newQuiz.setDescription(quizCreateDTO.getDescription());
        newQuiz.setDuration(quizCreateDTO.getDuration());
        newQuiz.setActive(quizCreateDTO.isActive());
        newQuiz.setCreateAt(LocalDateTime.now());

        newQuiz = quizRepository.save(newQuiz);

        return newQuiz != null;

    }

    @Override
    public boolean update(QuizEditDTO quizEditDTO) {
        if (quizEditDTO == null) {
            throw new IllegalArgumentException("Quiz update should not empty");
        }

        Quiz updateQuiz = quizRepository.findById(quizEditDTO.getId()).orElse(null);

        if (updateQuiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        updateQuiz.setTitle(quizEditDTO.getTitle());
        updateQuiz.setDescription(quizEditDTO.getDescription());
        updateQuiz.setDuration(quizEditDTO.getDuration());
        updateQuiz.setActive(quizEditDTO.isActive());
        updateQuiz.setUpdateAt(LocalDateTime.now());

        updateQuiz = quizRepository.save(updateQuiz);

        return updateQuiz != null;

    }

    @Override
    public boolean delete(UUID id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        quizRepository.delete(quiz);

        return !quizRepository.existsById(id);
    }

}
