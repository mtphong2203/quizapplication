package com.maiphong.quizapplication.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizEditDTO;
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
    public List<QuizDTO> getAll() {
        List<Quiz> quizzes = quizRepository.findAll();
        // convert dto to show view
        List<QuizDTO> quizDTOs = quizzes.stream().map(quiz -> {
            QuizDTO quizDTO = quizMapper.toQuizDTO(quiz);
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

        QuizDTO quizDTO = quizMapper.toQuizDTO(quiz);

        return quizDTO;

    }

    @Override
    public boolean create(QuizCreateDTO quizCreateDTO) {
        if (quizCreateDTO == null) {
            throw new IllegalArgumentException("Quiz create should not empty");
        }

        Quiz newQuiz = quizMapper.toQuiz(quizCreateDTO);

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

        quizMapper.toQuiz(quizEditDTO, updateQuiz);
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

    @Override
    public Page<QuizDTO> search(String keyword, Pageable pageable) {
        Specification<Quiz> spec = (root, query, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };

        Page<Quiz> quizPage = quizRepository.findAll(spec, pageable);

        Page<QuizDTO> quizPageDTO = quizPage.map(quiz -> {
            QuizDTO quizDTO = quizMapper.toQuizDTO(quiz);
            return quizDTO;
        });

        return quizPageDTO;
    }

}
