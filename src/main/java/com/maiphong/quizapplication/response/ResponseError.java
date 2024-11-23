package com.maiphong.quizapplication.response;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {

    private String message;

    private HttpStatus status;
}
