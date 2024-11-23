package com.maiphong.quizapplication.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maiphong.quizapplication.dtos.question.QuestionCreateDTO;
import com.maiphong.quizapplication.dtos.question.QuestionDTO;
import com.maiphong.quizapplication.dtos.question.QuestionEditDTO;
import com.maiphong.quizapplication.services.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/manager/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAll() {
        List<QuestionDTO> questionDTOs = questionService.getAll();

        if (questionDTOs == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(questionDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getById(@PathVariable UUID id) {
        QuestionDTO questionDTO = questionService.getById(id);

        if (questionDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(questionDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody QuestionCreateDTO questionCreateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        boolean isCreated = questionService.create(questionCreateDTO);

        if (!isCreated) {
            return ResponseEntity.badRequest().body(isCreated);
        }

        return ResponseEntity.ok(isCreated);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody QuestionEditDTO questionEditDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        boolean isUpdated = questionService.update(questionEditDTO);

        if (!isUpdated) {
            return ResponseEntity.badRequest().body(isUpdated);
        }

        return ResponseEntity.ok(isUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = questionService.delete(id);

        if (!isDeleted) {
            return ResponseEntity.badRequest().body(isDeleted);
        }

        return ResponseEntity.ok(isDeleted);
    }
}
