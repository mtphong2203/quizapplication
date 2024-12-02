package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.quizapplication.dtos.role.RoleCreateEditDTO;
import com.maiphong.quizapplication.dtos.role.RoleDTO;
import com.maiphong.quizapplication.dtos.role.RoleMasterDTO;
import com.maiphong.quizapplication.entities.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(RoleCreateEditDTO DTO);

    Role toEntity(RoleCreateEditDTO DTO, @MappingTarget Role role);

    RoleMasterDTO toMasterDTO(Role role);

    RoleDTO toDTO(Role role);

}
