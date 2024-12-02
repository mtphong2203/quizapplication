package com.maiphong.quizapplication.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.question.QuestionCreateEditDTO;
import com.maiphong.quizapplication.dtos.question.QuestionMasterDTO;
import com.maiphong.quizapplication.entities.Question;
import com.maiphong.quizapplication.entities.QuestionType;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.mappers.QuestionMapper;
import com.maiphong.quizapplication.repositories.QuestionRepository;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public List<QuestionMasterDTO> getAll() {
        List<Question> questions = questionRepository.findAll();

        List<QuestionMasterDTO> QuestionMasterDTOs = questions.stream().map(question -> {
            QuestionMasterDTO QuestionMasterDTO = questionMapper.toMasterDTO(question);

            return QuestionMasterDTO;
        }).toList();

        return QuestionMasterDTOs;
    }

    @Override
    public QuestionMasterDTO getById(UUID id) {
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            throw new ResourceNotFoundException("Question is not found");
        }
        QuestionMasterDTO QuestionMasterDTO = questionMapper.toMasterDTO(question);

        return QuestionMasterDTO;
    }

    @Override
    public QuestionMasterDTO create(QuestionCreateEditDTO questionDTO) {
        if (questionDTO == null) {
            throw new IllegalArgumentException("Question should not be null");
        }

        Question question = questionMapper.toEntity(questionDTO);
        question = questionRepository.save(question);

        return questionMapper.toMasterDTO(question);
    }

    @Override
    public QuestionMasterDTO update(UUID id, QuestionCreateEditDTO questionDTO) {
        if (questionDTO == null) {
            throw new IllegalArgumentException("Question should not be null");
        }

        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            throw new ResourceNotFoundException("Question is not found");
        }

        questionMapper.toEntity(questionDTO, question);
        question = questionRepository.save(question);

        return questionMapper.toMasterDTO(question);
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

    @Override
    public List<QuestionMasterDTO> searchByContent(String keyword) {
        Specification<Question> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(cb.lower(root.get("content")), "%" + keyword.toLowerCase() + "%");
        };

        List<Question> questions = questionRepository.findAll(spec);

        List<QuestionMasterDTO> masterDTOs = questions.stream().map(question -> {
            QuestionMasterDTO masterDTO = questionMapper.toMasterDTO(question);
            return masterDTO;
        }).toList();

        return masterDTOs;
    }

    @Override
    public Page<QuestionMasterDTO> searchPage(String keyword, Pageable pageable) {
        Specification<Question> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(cb.lower(root.get("content")), "%" + keyword.toLowerCase() + "%");
        };

        Page<Question> questions = questionRepository.findAll(spec, pageable);

        Page<QuestionMasterDTO> masterDTOs = questions.map(question -> {
            QuestionMasterDTO masterDTO = questionMapper.toMasterDTO(question);
            return masterDTO;
        });

        return masterDTOs;
    }

    @Override
    public List<QuestionMasterDTO> searchByType(QuestionType type) {
        Specification<Question> spec = (root, _, cb) -> {
            if (type == null) {
                return null;
            }

            return cb.equal(root.get("type"), type);
        };

        List<Question> questions = questionRepository.findAll(spec);

        List<QuestionMasterDTO> masterDTOs = questions.stream().map(question -> {
            QuestionMasterDTO masterDTO = questionMapper.toMasterDTO(question);
            return masterDTO;
        }).toList();

        return masterDTOs;
    }
}
