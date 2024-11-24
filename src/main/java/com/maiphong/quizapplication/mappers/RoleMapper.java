package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.maiphong.quizapplication.dtos.role.RoleCreateDTO;
import com.maiphong.quizapplication.dtos.role.RoleDTO;
import com.maiphong.quizapplication.dtos.role.RoleEditDTO;
import com.maiphong.quizapplication.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleCreateDTO roleCreateDTO);

    Role toRole(RoleEditDTO roleEditDTO, @MappingTarget Role role);

    RoleDTO toRoleDTO(Role role);

}
