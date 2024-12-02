package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.quizapplication.dtos.auth.RegisterRequestDTO;
import com.maiphong.quizapplication.dtos.user.UserCreateEditDTO;
import com.maiphong.quizapplication.dtos.user.UserDTO;
import com.maiphong.quizapplication.dtos.user.UserMasterDTO;
import com.maiphong.quizapplication.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateEditDTO DTO);

    User toEntity(RegisterRequestDTO DTO);

    User toEntity(UserCreateEditDTO DTO, @MappingTarget User user);

    UserMasterDTO toMasterDTO(User user);

    UserDTO toDTO(User user);
}
