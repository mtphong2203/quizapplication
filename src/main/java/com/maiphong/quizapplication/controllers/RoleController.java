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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maiphong.quizapplication.dtos.role.RoleCreateEditDTO;
import com.maiphong.quizapplication.dtos.role.RoleMasterDTO;
import com.maiphong.quizapplication.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/manager/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleMasterDTO>> getAll() {
        List<RoleMasterDTO> roleDTOs = roleService.getAll();

        if (roleDTOs == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(roleDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoleMasterDTO>> search(@RequestParam(required = false) String keyword) {
        List<RoleMasterDTO> roleDTOs = roleService.searchByName(keyword);
        if (roleDTOs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleMasterDTO> getById(@PathVariable UUID id) {
        RoleMasterDTO roleDTO = roleService.getById(id);

        if (roleDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(roleDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RoleCreateEditDTO roleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var masterDTOs = roleService.create(roleDTO);

        return ResponseEntity.ok(masterDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody RoleCreateEditDTO roleDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var masterDTOs = roleService.update(id, roleDTO);

        return ResponseEntity.ok(masterDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = roleService.delete(id);

        if (!isDeleted) {
            return ResponseEntity.badRequest().body(isDeleted);
        }

        return ResponseEntity.ok(isDeleted);
    }

}
