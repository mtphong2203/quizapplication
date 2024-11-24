package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import com.maiphong.quizapplication.dtos.user.UserCreateDTO;
import com.maiphong.quizapplication.dtos.user.UserDTO;
import com.maiphong.quizapplication.dtos.user.UserEditDTO;

public interface UserService {
    List<UserDTO> getAll();

    UserDTO getById(UUID id);

    boolean create(UserCreateDTO userCreateDTO);

    boolean update(UserEditDTO userEditDTO);

    boolean delete(UUID id);
}
