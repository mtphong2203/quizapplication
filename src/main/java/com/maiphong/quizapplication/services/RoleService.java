package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import com.maiphong.quizapplication.dtos.role.RoleCreateEditDTO;
import com.maiphong.quizapplication.dtos.role.RoleMasterDTO;

public interface RoleService {
    List<RoleMasterDTO> getAll();

    List<RoleMasterDTO> searchByName(String keyword);

    RoleMasterDTO getById(UUID id);

    RoleMasterDTO create(RoleCreateEditDTO roleDTO);

    RoleMasterDTO update(UUID id, RoleCreateEditDTO roleDTO);

    boolean delete(UUID id);
}
