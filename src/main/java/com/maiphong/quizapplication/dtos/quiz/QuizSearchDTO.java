package com.maiphong.quizapplication.dtos.quiz;

import com.maiphong.quizapplication.dtos.search.SearchDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSearchDTO extends SearchDTO {
    private String keyword;
}
