package com.maiphong.quizapplication.dtos.answer;

import com.maiphong.quizapplication.dtos.MasterCreateEditDTO;

import lombok.*;

@Getter
@Setter
public class AnswerCreateEditDTO extends MasterCreateEditDTO {

    private String content;

    private boolean isCorrect;
}
