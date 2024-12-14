package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import com.maiphong.quizapplication.dtos.answer.AnswerCreateEditDTO;
import com.maiphong.quizapplication.dtos.answer.AnswerMasterDTO;

public interface AnswerService {
    List<AnswerMasterDTO> getAll();

    AnswerMasterDTO getById(UUID id);

    AnswerMasterDTO create(AnswerCreateEditDTO masterDTO);

    AnswerMasterDTO update(UUID id, AnswerCreateEditDTO masterDTO);

    boolean delete(UUID id);
}
