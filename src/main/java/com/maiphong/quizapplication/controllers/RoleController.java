package com.maiphong.quizapplication.controllers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
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
import com.maiphong.quizapplication.mappers.CustomPageData;
import com.maiphong.quizapplication.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/manager/roles")
public class RoleController {

    private final RoleService roleService;
    private final PagedResourcesAssembler<RoleMasterDTO> pageResource;

    public RoleController(RoleService roleService, PagedResourcesAssembler<RoleMasterDTO> pageResource) {
        this.roleService = roleService;
        this.pageResource = pageResource;
    }

    @GetMapping
    public ResponseEntity<List<RoleMasterDTO>> getAll() {
        List<RoleMasterDTO> roleDTOs = roleService.getAll();

        if (roleDTOs == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(roleDTOs);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<RoleMasterDTO>> search(@RequestParam(required = false) String keyword) {
        List<RoleMasterDTO> roleDTOs = roleService.searchByName(keyword);
        if (roleDTOs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roleDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPage(@RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
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

        var masterDTOs = roleService.search(keyword, pageable);

        var pageModel = pageResource.toModel(masterDTOs);

        Collection<EntityModel<RoleMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomPageData<EntityModel<RoleMasterDTO>>(data, pageModel.getMetadata(), links);

        return ResponseEntity.ok(response);
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
