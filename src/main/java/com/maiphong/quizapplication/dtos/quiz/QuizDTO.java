package com.maiphong.quizapplication.dtos.quiz;

import com.maiphong.quizapplication.dtos.BaseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO extends BaseDTO {

    private String title;

    private String description;

    private double duration;

    private boolean isActive;
}
