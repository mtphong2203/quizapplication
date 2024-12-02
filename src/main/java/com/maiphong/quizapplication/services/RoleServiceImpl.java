package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.role.RoleCreateEditDTO;
import com.maiphong.quizapplication.dtos.role.RoleMasterDTO;
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
    public List<RoleMasterDTO> getAll() {
        List<Role> roles = roleRepository.findAll();

        List<RoleMasterDTO> roleDTOs = roles.stream().map(role -> {
            RoleMasterDTO roleDTO = roleMapper.toMasterDTO(role);
            return roleDTO;
        }).toList();
        return roleDTOs;
    }

    @Override
    public RoleMasterDTO getById(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not found");
        }
        RoleMasterDTO roleDTO = roleMapper.toMasterDTO(role);

        return roleDTO;
    }

    @Override
    public RoleMasterDTO create(RoleCreateEditDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role create not null");
        }
        Role role = roleRepository.findByName(roleDTO.getName());

        if (role != null) {
            throw new IllegalArgumentException("Role is already exist!");
        }

        Role newRole = roleMapper.toEntity(roleDTO);

        newRole = roleRepository.save(newRole);

        return roleMapper.toMasterDTO(newRole);
    }

    @Override
    public RoleMasterDTO update(UUID id, RoleCreateEditDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role create not null");
        }

        Role updateRole = roleRepository.findById(id).orElse(null);

        if (updateRole == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }
        roleMapper.toEntity(roleDTO, updateRole);

        updateRole = roleRepository.save(updateRole);

        return roleMapper.toMasterDTO(updateRole);
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

    @Override
    public List<RoleMasterDTO> searchByName(String keyword) {
        Specification<Role> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };

        List<Role> roles = roleRepository.findAll(spec);

        List<RoleMasterDTO> masterDTOs = roles.stream().map(role -> {
            RoleMasterDTO masterDTO = roleMapper.toMasterDTO(role);
            return masterDTO;
        }).toList();

        return masterDTOs;
    }

}
