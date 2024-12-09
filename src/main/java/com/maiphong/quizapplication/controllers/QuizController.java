package com.maiphong.quizapplication.controllers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateEditDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizMasterDTO;
import com.maiphong.quizapplication.mappers.CustomPageData;
import com.maiphong.quizapplication.services.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/manager/quizzes")
public class QuizController {
    private final QuizService quizService;
    private final PagedResourcesAssembler<QuizMasterDTO> pagedResourcesAssembler;

    public QuizController(QuizService quizService, PagedResourcesAssembler<QuizMasterDTO> pagedResourcesAssembler) {
        this.quizService = quizService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<List<QuizMasterDTO>> getAll() {
        List<QuizMasterDTO> quizDTOs = quizService.getAll();
        if (quizDTOs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizDTOs);
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<List<QuizMasterDTO>> searchByKeyword(@RequestParam(required = false) String keyword) {
        List<QuizMasterDTO> quizDTOs = quizService.searchByTitle(keyword);
        if (quizDTOs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPage(@RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        Pageable pageable = null;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        Page<QuizMasterDTO> quizzes = quizService.search(keyword, pageable);

        var pageModel = pagedResourcesAssembler.toModel(quizzes);

        Collection<EntityModel<QuizMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomPageData<EntityModel<QuizMasterDTO>>(data, pageModel.getMetadata(), links);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizMasterDTO> getById(@PathVariable UUID id) {
        QuizMasterDTO quizDTO = quizService.getById(id);
        if (quizDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody QuizCreateEditDTO quizDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var masterDTO = quizService.create(quizDTO);

        return ResponseEntity.ok(masterDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody QuizCreateEditDTO quizDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var masterDTO = quizService.update(id, quizDTO);

        return ResponseEntity.ok(masterDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = quizService.delete(id);

        if (!isDeleted) {
            return ResponseEntity.badRequest().body(isDeleted);
        }

        return ResponseEntity.ok(isDeleted);
    }
}
