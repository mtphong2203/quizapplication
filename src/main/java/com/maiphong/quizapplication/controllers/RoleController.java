package com.maiphong.quizapplication.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maiphong.quizapplication.dtos.role.RoleCreateDTO;
import com.maiphong.quizapplication.dtos.role.RoleDTO;
import com.maiphong.quizapplication.dtos.role.RoleEditDTO;
import com.maiphong.quizapplication.services.RoleService;

@RestController
@RequestMapping("api/manager/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAll() {
        List<RoleDTO> roleDTOs = roleService.getAll();

        if (roleDTOs == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(roleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable UUID id) {
        RoleDTO roleDTO = roleService.getById(id);

        if (roleDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(roleDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RoleCreateDTO roleCreateDTO) {
        boolean isCreated = roleService.create(roleCreateDTO);

        if (!isCreated) {
            return ResponseEntity.badRequest().body(isCreated);
        }

        return ResponseEntity.status(201).body(isCreated);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody RoleEditDTO roleEditDTO) {
        boolean isUpdated = roleService.update(roleEditDTO);

        if (!isUpdated) {
            return ResponseEntity.badRequest().body(isUpdated);
        }

        return ResponseEntity.ok(isUpdated);
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
