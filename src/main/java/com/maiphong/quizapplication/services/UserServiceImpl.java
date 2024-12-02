package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.user.UserCreateEditDTO;
import com.maiphong.quizapplication.dtos.user.UserMasterDTO;
import com.maiphong.quizapplication.entities.User;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.mappers.UserMapper;
import com.maiphong.quizapplication.repositories.UserRepository;

import jakarta.persistence.criteria.Predicate;

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
    public List<UserMasterDTO> getAll() {
        List<User> users = userRepository.findAll();

        List<UserMasterDTO> userDTOs = users.stream().map(user -> {
            UserMasterDTO userDTO = userMapper.toMasterDTO(user);

            return userDTO;
        }).toList();

        return userDTOs;
    }

    @Override
    public UserMasterDTO getById(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not found");
        }

        UserMasterDTO userDTO = userMapper.toMasterDTO(user);

        return userDTO;
    }

    @Override
    public UserMasterDTO create(UserCreateEditDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User create should not be null");
        }

        User user = userRepository.findByUsername(userDTO.getUsername());

        if (user != null) {
            throw new IllegalArgumentException("User name is already exists!");
        }

        User newUser = userMapper.toEntity(userDTO);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        newUser = userRepository.save(newUser);

        return userMapper.toMasterDTO(newUser);

    }

    @Override
    public UserMasterDTO update(UUID id, UserCreateEditDTO userEditDTO) {
        if (userEditDTO == null) {
            throw new IllegalArgumentException("User update should not be null");
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        userMapper.toEntity(userEditDTO, user);
        user.setPassword(passwordEncoder.encode(userEditDTO.getPassword()));

        user = userRepository.save(user);

        return userMapper.toMasterDTO(user);
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

    @Override
    public List<UserMasterDTO> searchByKeyword(String keyword) {
        Specification<User> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            Predicate predicate = cb.like(cb.lower(root.get("username")), "%" + keyword.toLowerCase() + "%");

            predicate = cb.or(predicate, cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%"));

            predicate = cb.or(predicate, cb.like(cb.lower(root.get("phoneNumber")), "%" + keyword.toLowerCase() + "%"));

            return predicate;

        };

        List<User> users = userRepository.findAll(spec);

        List<UserMasterDTO> userDTOs = users.stream().map(user -> {
            UserMasterDTO userDTO = userMapper.toMasterDTO(user);
            return userDTO;
        }).toList();

        return userDTOs;
    }

    @Override
    public Page<UserMasterDTO> search(String keyword, Pageable pageable) {
        Specification<User> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(cb.lower(root.get("username")), "%" + keyword.toLowerCase() + "%");
        };

        Page<User> users = userRepository.findAll(spec, pageable);

        Page<UserMasterDTO> userDTOs = users.map(user -> {
            UserMasterDTO userDTO = userMapper.toMasterDTO(user);
            return userDTO;
        });

        return userDTOs;
    }
}
