package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;

import com.maiphong.quizapplication.dtos.answer.AnswerDTO;
import com.maiphong.quizapplication.entities.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer toAnswer(AnswerDTO answerDTO);

    AnswerDTO toAnswerDTO(Answer answer);
}
