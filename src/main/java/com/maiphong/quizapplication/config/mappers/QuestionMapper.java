package com.maiphong.quizapplication.config.mappers;

import org.mapstruct.Mapper;

import com.maiphong.quizapplication.dtos.question.QuestionCreateDTO;
import com.maiphong.quizapplication.dtos.question.QuestionDTO;
import com.maiphong.quizapplication.entities.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionDTO toQuestionDTO(Question question);

    Question toQuestion(QuestionCreateDTO questionCreateDTO);

}
