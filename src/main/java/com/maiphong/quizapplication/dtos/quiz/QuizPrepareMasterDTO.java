package com.maiphong.quizapplication.dtos.quiz;

import com.maiphong.quizapplication.dtos.BaseDTO;
import com.maiphong.quizapplication.dtos.user.UserMasterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizPrepareMasterDTO extends BaseDTO {

    private String title;

    private String description;

    private double duration;

    private boolean isActive;

    private String thumbnailUrl;

    private String quizCode;

    private UserMasterDTO userMasterDTO;

}
