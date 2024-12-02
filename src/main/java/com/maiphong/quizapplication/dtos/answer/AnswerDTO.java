package com.maiphong.quizapplication.dtos.answer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

    private String content;

    private boolean isCorrect;
}
