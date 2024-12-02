package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateEditDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizMasterDTO;
import com.maiphong.quizapplication.entities.Quiz;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.mappers.QuizMapper;
import com.maiphong.quizapplication.repositories.QuizRepository;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    public QuizServiceImpl(QuizRepository quizRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    @Override
    public List<QuizMasterDTO> getAll() {
        List<Quiz> quizzes = quizRepository.findAll();
        // convert dto to show view
        List<QuizMasterDTO> quizDTOs = quizzes.stream().map(quiz -> {
            QuizMasterDTO quizDTO = quizMapper.toMasterDTO(quiz);
            return quizDTO;
        }).toList();

        return quizDTOs;
    }

    @Override
    public QuizMasterDTO getById(UUID id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        QuizMasterDTO quizDTO = quizMapper.toMasterDTO(quiz);

        return quizDTO;

    }

    @Override
    public QuizMasterDTO create(QuizCreateEditDTO quizDTO) {
        if (quizDTO == null) {
            throw new IllegalArgumentException("Quiz create should not empty");
        }

        Quiz newQuiz = quizMapper.toEntity(quizDTO);

        newQuiz = quizRepository.save(newQuiz);

        return quizMapper.toMasterDTO(newQuiz);

    }

    @Override
    public QuizMasterDTO update(UUID id, QuizCreateEditDTO quizDTO) {
        if (quizDTO == null) {
            throw new IllegalArgumentException("Quiz update should not empty");
        }

        Quiz updateQuiz = quizRepository.findById(id).orElse(null);

        if (updateQuiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        quizMapper.toEntity(quizDTO, updateQuiz);
        updateQuiz = quizRepository.save(updateQuiz);

        return quizMapper.toMasterDTO(updateQuiz);

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

    @Override
    public Page<QuizMasterDTO> search(String keyword, Pageable pageable) {
        Specification<Quiz> spec = (root, _, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };

        Page<Quiz> quizPage = quizRepository.findAll(spec, pageable);

        Page<QuizMasterDTO> quizPageDTO = quizPage.map(quiz -> {
            QuizMasterDTO quizDTO = quizMapper.toMasterDTO(quiz);
            return quizDTO;
        });

        return quizPageDTO;
    }

    @Override
    public List<QuizMasterDTO> searchByTitle(String keyword) {
        Specification<Quiz> spec = (root, _, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };

        List<Quiz> quizs = quizRepository.findAll(spec);

        List<QuizMasterDTO> masterDTOs = quizs.stream().map(quiz -> {
            QuizMasterDTO quizDTO = quizMapper.toMasterDTO(quiz);
            return quizDTO;
        }).toList();

        return masterDTOs;
    }

}
