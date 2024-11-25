package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.user.UserCreateDTO;
import com.maiphong.quizapplication.dtos.user.UserDTO;
import com.maiphong.quizapplication.dtos.user.UserEditDTO;
import com.maiphong.quizapplication.entities.User;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.mappers.UserMapper;
import com.maiphong.quizapplication.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();

        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO userDTO = userMapper.toUserDTO(user);

            return userDTO;
        }).toList();

        return userDTOs;
    }

    @Override
    public UserDTO getById(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not found");
        }

        UserDTO userDTO = userMapper.toUserDTO(user);

        return userDTO;
    }

    @Override
    public boolean create(UserCreateDTO userCreateDTO) {
        if (userCreateDTO == null) {
            throw new IllegalArgumentException("User create should not be null");
        }

        User newUser = userMapper.toUser(userCreateDTO);
        newUser.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        newUser = userRepository.save(newUser);

        return newUser != null;

    }

    @Override
    public boolean update(UserEditDTO userEditDTO) {
        if (userEditDTO == null) {
            throw new IllegalArgumentException("User update should not be null");
        }

        User user = userRepository.findById(userEditDTO.getId()).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        userMapper.toUser(userEditDTO, user);
        user.setPassword(passwordEncoder.encode(userEditDTO.getPassword()));

        user = userRepository.save(user);

        return user != null;
    }

    @Override
    public boolean delete(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        userRepository.delete(user);

        return !userRepository.existsById(id);

    }

}
