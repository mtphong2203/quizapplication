package com.maiphong.quizapplication.controllers;

import java.util.List;
import java.util.UUID;

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

import com.maiphong.quizapplication.dtos.user.UserCreateEditDTO;
import com.maiphong.quizapplication.dtos.user.UserMasterDTO;
import com.maiphong.quizapplication.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/manager/users")
public class UserController {
    private final UserService userService;
    private final PagedResourcesAssembler<UserMasterDTO> pagedResource;

    public UserController(UserService userService, PagedResourcesAssembler<UserMasterDTO> pagedResource) {
        this.userService = userService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    public ResponseEntity<List<UserMasterDTO>> getAll() {
        List<UserMasterDTO> userDTOs = userService.getAll();
        if (userDTOs == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<UserMasterDTO>> search(@RequestParam(required = false) String keyword) {
        List<UserMasterDTO> userDTOs = userService.searchByKeyword(keyword);
        if (userDTOs == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPage(@RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "username") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        if (keyword == null) {
            getAll();
        }

        Pageable pageable = null;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var masterDTOs = userService.search(keyword, pageable);

        return ResponseEntity.ok(pagedResource.toModel(masterDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMasterDTO> getById(@PathVariable UUID id) {
        UserMasterDTO userDTO = userService.getById(id);

        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateEditDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        var masterDTO = userService.create(userDTO);

        return ResponseEntity.ok(masterDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody UserCreateEditDTO userDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        var masterDTO = userService.update(id, userDTO);

        return ResponseEntity.ok(masterDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = userService.delete(id);

        if (!isDeleted) {
            return ResponseEntity.badRequest().body(isDeleted);
        }

        return ResponseEntity.ok(isDeleted);
    }

}
