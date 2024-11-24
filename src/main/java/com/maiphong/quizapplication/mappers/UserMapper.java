package com.maiphong.quizapplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.maiphong.quizapplication.dtos.user.UserCreateDTO;
import com.maiphong.quizapplication.dtos.user.UserDTO;
import com.maiphong.quizapplication.dtos.user.UserEditDTO;
import com.maiphong.quizapplication.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateDTO userCreateDTO);

    UserDTO toUserDTO(User user);

    User toUser(UserEditDTO userEditDTO, @MappingTarget User user);
}
