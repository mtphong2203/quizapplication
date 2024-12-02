package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.quizapplication.dtos.question.QuestionCreateEditDTO;
import com.maiphong.quizapplication.dtos.question.QuestionDTO;
import com.maiphong.quizapplication.dtos.question.QuestionMasterDTO;
import com.maiphong.quizapplication.entities.Question;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    Question toEntity(QuestionCreateEditDTO DTO);

    Question toEntity(QuestionCreateEditDTO DTO, @MappingTarget Question question);

    QuestionDTO toDTO(Question entity);

    QuestionMasterDTO toMasterDTO(Question entity);

}
