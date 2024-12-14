package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.answer.AnswerCreateEditDTO;
import com.maiphong.quizapplication.dtos.answer.AnswerMasterDTO;
import com.maiphong.quizapplication.entities.Answer;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.mappers.AnswerMapper;
import com.maiphong.quizapplication.repositories.AnswerRepository;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    @Override
    public List<AnswerMasterDTO> getAll() {
        var answers = answerRepository.findAll();

        var masterDTOs = answers.stream().map(answer -> {
            AnswerMasterDTO masterDTO = answerMapper.toAnswerDTO(answer);
            return masterDTO;
        }).toList();

        return masterDTOs;
    }

    @Override
    public AnswerMasterDTO getById(UUID id) {
        var answer = answerRepository.findById(id).orElse(null);

        if (answer == null) {
            throw new ResourceNotFoundException("Answer is not found");
        }
        AnswerMasterDTO masterDTO = answerMapper.toAnswerDTO(answer);
        return masterDTO;
    }

    @Override
    public AnswerMasterDTO create(AnswerCreateEditDTO masterDTO) {
        if (masterDTO == null) {
            throw new IllegalArgumentException("Answer is required");
        }

        Answer newAnswer = answerMapper.toEntity(masterDTO);

        newAnswer = answerRepository.save(newAnswer);

        return answerMapper.toAnswerDTO(newAnswer);

    }

    @Override
    public AnswerMasterDTO update(UUID id, AnswerCreateEditDTO masterDTO) {
        if (masterDTO == null) {
            throw new IllegalArgumentException("Answer is required");
        }

        Answer answer = answerRepository.findById(id).orElse(null);

        if (answer == null) {
            throw new ResourceNotFoundException("Answer is not found");
        }

        answer = answerMapper.toEntity(masterDTO, answer);

        answer = answerRepository.save(answer);

        return answerMapper.toAnswerDTO(answer);
    }

    @Override
    public boolean delete(UUID id) {
        Answer answer = answerRepository.findById(id).orElse(null);

        if (answer == null) {
            throw new ResourceNotFoundException("Answer is not found");
        }

        answerRepository.delete(answer);

        return !answerRepository.existsById(id);
    }

}
