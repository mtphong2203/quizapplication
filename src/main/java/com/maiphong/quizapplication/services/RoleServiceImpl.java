package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.role.RoleCreateDTO;
import com.maiphong.quizapplication.dtos.role.RoleDTO;
import com.maiphong.quizapplication.dtos.role.RoleEditDTO;
import com.maiphong.quizapplication.entities.Role;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.mappers.RoleMapper;
import com.maiphong.quizapplication.repositories.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> getAll() {
        List<Role> roles = roleRepository.findAll();

        List<RoleDTO> roleDTOs = roles.stream().map(role -> {
            RoleDTO roleDTO = roleMapper.toRoleDTO(role);

            return roleDTO;
        }).toList();
        return roleDTOs;
    }

    @Override
    public RoleDTO getById(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not found");
        }

        RoleDTO roleDTO = roleMapper.toRoleDTO(role);

        return roleDTO;
    }

    @Override
    public boolean create(RoleCreateDTO roleCreateDTO) {
        if (roleCreateDTO == null) {
            throw new IllegalArgumentException("Role create not null");
        }

        Role newRole = roleMapper.toRole(roleCreateDTO);

        newRole = roleRepository.save(newRole);

        return newRole != null;
    }

    @Override
    public boolean update(RoleEditDTO roleEditDTO) {
        if (roleEditDTO == null) {
            throw new IllegalArgumentException("Role create not null");
        }

        Role updateRole = roleRepository.findById(roleEditDTO.getId()).orElse(null);

        if (updateRole == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }
        roleMapper.toRole(roleEditDTO, updateRole);

        updateRole = roleRepository.save(updateRole);

        return updateRole != null;
    }

    @Override
    public boolean delete(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }

        roleRepository.delete(role);

        return !roleRepository.existsById(id);
    }

}
