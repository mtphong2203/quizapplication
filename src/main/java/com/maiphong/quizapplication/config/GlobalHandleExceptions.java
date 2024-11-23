package com.maiphong.quizapplication.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.response.ResponseError;

@ControllerAdvice
public class GlobalHandleExceptions {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handleIllegalArgumentException(IllegalArgumentException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handleResourceNotFoundException(ResourceNotFoundException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
