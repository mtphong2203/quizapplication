package com.maiphong.quizapplication.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maiphong.quizapplication.dtos.question.QuestionCreateEditDTO;
import com.maiphong.quizapplication.dtos.question.QuestionMasterDTO;
import com.maiphong.quizapplication.entities.QuestionType;
import com.maiphong.quizapplication.services.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/manager/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final PagedResourcesAssembler<QuestionMasterDTO> pageMaster;

    public QuestionController(QuestionService questionService, PagedResourcesAssembler<QuestionMasterDTO> pageMaster) {
        this.questionService = questionService;
        this.pageMaster = pageMaster;
    }

    @GetMapping
    public ResponseEntity<List<QuestionMasterDTO>> getAll() {
        List<QuestionMasterDTO> questionDTOs = questionService.getAll();

        if (questionDTOs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionDTOs);
    }

    @GetMapping("/searchByContent")
    public ResponseEntity<List<QuestionMasterDTO>> searchByContent(@RequestParam(required = false) String keyword) {
        List<QuestionMasterDTO> questionDTOs = questionService.searchByContent(keyword);

        if (questionDTOs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionDTOs);
    }

    @GetMapping("/searchByType")
    public ResponseEntity<List<QuestionMasterDTO>> searchByType(@RequestParam(required = false) QuestionType type) {
        List<QuestionMasterDTO> questionDTOs = questionService.searchByType(type);

        if (questionDTOs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPage(@RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "content") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        Page<QuestionMasterDTO> questions = questionService.searchPage(keyword, pageable);

        return ResponseEntity.ok(pageMaster.toModel(questions));

    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionMasterDTO> getById(@PathVariable UUID id) {
        QuestionMasterDTO questionDTO = questionService.getById(id);

        if (questionDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(questionDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody QuestionCreateEditDTO questionDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        var masterDTO = questionService.create(questionDTO);
        return ResponseEntity.ok(masterDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody QuestionCreateEditDTO questionDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var masterDTO = questionService.update(id, questionDTO);
        return ResponseEntity.ok(masterDTO);
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
