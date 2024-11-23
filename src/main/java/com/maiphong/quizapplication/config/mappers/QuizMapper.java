package com.maiphong.quizapplication.config.mappers;

import org.mapstruct.Mapper;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizEditDTO;
import com.maiphong.quizapplication.entities.Quiz;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    QuizDTO toQuizDTO(Quiz quiz);

    Quiz toQuiz(QuizCreateDTO quizCreateDTO);

    Quiz toQuiz(QuizEditDTO quizEditDTO);

}
