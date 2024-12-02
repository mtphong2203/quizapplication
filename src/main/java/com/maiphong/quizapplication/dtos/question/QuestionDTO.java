package com.maiphong.quizapplication.dtos.question;

import com.maiphong.quizapplication.dtos.BaseDTO;
import com.maiphong.quizapplication.entities.QuestionType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO extends BaseDTO {

    private String content;

    private QuestionType type;

}
