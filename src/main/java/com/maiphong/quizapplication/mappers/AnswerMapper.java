package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.quizapplication.dtos.answer.AnswerCreateEditDTO;
import com.maiphong.quizapplication.dtos.answer.AnswerMasterDTO;
import com.maiphong.quizapplication.entities.Answer;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    Answer toEntity(AnswerCreateEditDTO DTO);

    Answer toEntity(AnswerCreateEditDTO answerDTO, @MappingTarget Answer entity);

    AnswerMasterDTO toAnswerDTO(Answer entity);

}
