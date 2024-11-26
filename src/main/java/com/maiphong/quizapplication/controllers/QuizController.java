package com.maiphong.quizapplication.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizEditDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizSearchDTO;
import com.maiphong.quizapplication.services.QuizService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/manager/quizzes")
public class QuizController {
    private final QuizService quizService;
    private final PagedResourcesAssembler<QuizDTO> pagedResourcesAssembler;

    public QuizController(QuizService quizService, PagedResourcesAssembler<QuizDTO> pagedResourcesAssembler) {
        this.quizService = quizService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAll() {
        List<QuizDTO> quizDTOs = quizService.getAll();

        if (quizDTOs == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(quizDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getById(@PathVariable UUID id) {
        QuizDTO quizDTO = quizService.getById(id);

        if (quizDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(quizDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody QuizCreateDTO quizCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        boolean isCreated = quizService.create(quizCreateDTO);

        if (!isCreated) {
            return ResponseEntity.badRequest().body(isCreated);
        }

        return ResponseEntity.ok(isCreated);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody QuizEditDTO quizEditDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        boolean isUpdated = quizService.update(quizEditDTO);

        if (!isUpdated) {
            return ResponseEntity.badRequest().body(isUpdated);
        }

        return ResponseEntity.ok(isUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = quizService.delete(id);

        if (!isDeleted) {
            return ResponseEntity.badRequest().body(isDeleted);
        }

        return ResponseEntity.ok(isDeleted);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody QuizSearchDTO quizSearchDTO) {
        Pageable pageable = PageRequest.of(quizSearchDTO.getPage(), quizSearchDTO.getSize());

        var quizPageDTO = quizService.search(quizSearchDTO.getKeyword(), pageable);

        var pageModel = pagedResourcesAssembler.toModel(quizPageDTO);

        return ResponseEntity.ok(pageModel);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "2") Integer size) {
        // Check sort order
        Pageable pageable = PageRequest.of(page, size);

        // Search category by keyword and paging
        var quizzes = quizService.search(keyword, pageable);

        return ResponseEntity.ok(quizzes);
    }
}
