package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import com.maiphong.quizapplication.dtos.role.RoleCreateDTO;
import com.maiphong.quizapplication.dtos.role.RoleDTO;
import com.maiphong.quizapplication.dtos.role.RoleEditDTO;

public interface RoleService {
    List<RoleDTO> getAll();

    RoleDTO getById(UUID id);

    boolean create(RoleCreateDTO roleCreateDTO);

    boolean update(RoleEditDTO roleEditDTO);

    boolean delete(UUID id);
}
