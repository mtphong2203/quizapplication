package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.quizapplication.dtos.user.UserCreateEditDTO;
import com.maiphong.quizapplication.dtos.user.UserMasterDTO;

public interface UserService {
    List<UserMasterDTO> getAll();

    List<UserMasterDTO> searchByKeyword(String keyword);

    Page<UserMasterDTO> search(String keyword, Pageable pageable);

    UserMasterDTO getById(UUID id);

    UserMasterDTO create(UserCreateEditDTO userDTO);

    UserMasterDTO update(UUID id, UserCreateEditDTO userDTO);

    boolean delete(UUID id);
}
