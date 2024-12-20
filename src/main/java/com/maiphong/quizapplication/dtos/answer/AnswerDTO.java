package com.maiphong.quizapplication.dtos.answer;

import com.maiphong.quizapplication.dtos.BaseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO extends BaseDTO {

    private String content;

    private boolean isCorrect;
}
