package com.maiphong.quizapplication.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.catalina.connector.Response;
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

import com.maiphong.quizapplication.dtos.answer.AnswerCreateEditDTO;
import com.maiphong.quizapplication.dtos.answer.AnswerMasterDTO;
import com.maiphong.quizapplication.services.AnswerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/manager/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<List<AnswerMasterDTO>> getAll() {
        var masterDTOs = answerService.getAll();
        return ResponseEntity.ok(masterDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerMasterDTO> getById(@PathVariable UUID id) {
        var masterDTO = answerService.getById(id);
        return ResponseEntity.ok(masterDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AnswerCreateEditDTO answerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var masterDTO = answerService.create(answerDTO);
        return ResponseEntity.status(201).body(masterDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> create(@PathVariable UUID id, @Valid @RequestBody AnswerCreateEditDTO answerDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var masterDTO = answerService.update(id, answerDTO);
        return ResponseEntity.ok(masterDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var isDeleted = answerService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}
