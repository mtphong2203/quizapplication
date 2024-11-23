package com.maiphong.quizapplication.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.question.QuestionCreateDTO;
import com.maiphong.quizapplication.dtos.question.QuestionDTO;
import com.maiphong.quizapplication.dtos.question.QuestionEditDTO;
import com.maiphong.quizapplication.entities.Question;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.repositories.QuestionRepository;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<QuestionDTO> getAll() {
        List<Question> questions = questionRepository.findAll();

        List<QuestionDTO> questionDTOs = questions.stream().map(question -> {
            QuestionDTO questionDTO = new QuestionDTO();

            questionDTO.setId(question.getId());
            questionDTO.setContent(question.getContent());
            questionDTO.setQuestionType(question.getQuestionType());
            questionDTO.setActive(question.isActive());

            return questionDTO;
        }).toList();

        return questionDTOs;
    }

    @Override
    public QuestionDTO getById(UUID id) {
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            throw new ResourceNotFoundException("Quetion is not found");
        }
        QuestionDTO questionDTO = new QuestionDTO();

        questionDTO.setId(question.getId());
        questionDTO.setContent(question.getContent());
        questionDTO.setQuestionType(question.getQuestionType());
        questionDTO.setActive(question.isActive());

        return questionDTO;
    }

    @Override
    public boolean create(QuestionCreateDTO questionCreateDTO) {
        if (questionCreateDTO == null) {
            throw new IllegalArgumentException("Question should not be null");
        }

        Question question = new Question();
        question.setContent(questionCreateDTO.getContent());
        question.setQuestionType(questionCreateDTO.getQuestionType());
        question.setActive(questionCreateDTO.isActive());
        question.setCreateAt(LocalDateTime.now());

        question = questionRepository.save(question);

        return question != null;
    }

    @Override
    public boolean update(QuestionEditDTO questionEditDTO) {
        if (questionEditDTO == null) {
            throw new IllegalArgumentException("Question should not be null");
        }

        Question question = questionRepository.findById(questionEditDTO.getId()).orElse(null);

        if (question == null) {
            throw new ResourceNotFoundException("Question is not found");
        }
        question.setContent(questionEditDTO.getContent());
        question.setQuestionType(questionEditDTO.getQuestionType());
        question.setActive(questionEditDTO.isActive());
        question.setUpdateAt(LocalDateTime.now());

        question = questionRepository.save(question);

        return question != null;
    }

    @Override
    public boolean delete(UUID id) {
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            throw new ResourceNotFoundException("Question is not found");
        }
        questionRepository.delete(question);

        return !questionRepository.existsById(id);
    }

}
