package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateEditDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizMasterDTO;
import com.maiphong.quizapplication.entities.Quiz;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizMapper {

    Quiz toEntity(QuizCreateEditDTO DTO);

    Quiz toEntity(QuizCreateEditDTO DTO, @MappingTarget Quiz quiz);

    QuizMasterDTO toMasterDTO(Quiz quiz);

    QuizDTO toDTO(Quiz quiz);
}
