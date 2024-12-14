package com.maiphong.quizapplication.dtos.answer;

import com.maiphong.quizapplication.dtos.MasterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerMasterDTO extends MasterDTO {
    private String content;

    private boolean isCorrect;
}
