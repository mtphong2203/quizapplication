package com.maiphong.quizapplication.dtos.question;

import com.maiphong.quizapplication.dtos.MasterDTO;
import com.maiphong.quizapplication.entities.QuestionType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionMasterDTO extends MasterDTO {

    private String content;

    private QuestionType type;
}
