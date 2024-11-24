package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.maiphong.quizapplication.dtos.question.QuestionCreateDTO;
import com.maiphong.quizapplication.dtos.question.QuestionDTO;
import com.maiphong.quizapplication.dtos.question.QuestionEditDTO;
import com.maiphong.quizapplication.entities.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionDTO toQuestionDTO(Question question);

    Question toQuestion(QuestionCreateDTO questionCreateDTO);

    Question toQuestion(QuestionEditDTO questionEditDTO, @MappingTarget Question question);

}
