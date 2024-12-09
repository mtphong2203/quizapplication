package com.maiphong.quizapplication.dtos.quiz;

import com.maiphong.quizapplication.dtos.MasterDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizMasterDTO extends MasterDTO {

    private String title;

    private String description;

    private double duration;

}
