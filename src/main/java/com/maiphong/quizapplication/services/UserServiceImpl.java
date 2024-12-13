package com.maiphong.quizapplication.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.user.UserCreateEditDTO;
import com.maiphong.quizapplication.dtos.user.UserMasterDTO;
import com.maiphong.quizapplication.entities.Role;
import com.maiphong.quizapplication.entities.User;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.mappers.UserMapper;
import com.maiphong.quizapplication.repositories.RoleRepository;
import com.maiphong.quizapplication.repositories.UserRepository;

import jakarta.persistence.criteria.Predicate;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserMasterDTO> getAll() {
        List<User> users = userRepository.findAll();

        List<UserMasterDTO> userDTOs = users.stream().map(user -> {
            UserMasterDTO userDTO = userMapper.toMasterDTO(user);
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                Set<String> roleNames = user.getRoles().stream()
                        .map(role -> role.getName()) // Giả sử Role có phương thức getName()
                        .collect(Collectors.toSet());
                userDTO.setRole(roleNames);
            }

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
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<String> roleNames = user.getRoles().stream()
                    .map(role -> role.getName()) // Giả sử Role có phương thức getName()
                    .collect(Collectors.toSet());
            userDTO.setRole(roleNames);
        }

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

        if (userDTO.getRoleId() != null) {
            var roles = roleRepository.findById(userDTO.getRoleId());
            if (roles.isPresent()) {
                newUser.setRoles(Collections.singleton(roles.get()));
            }
        }

        newUser = userRepository.save(newUser);

        UserMasterDTO userMasterDTO = userMapper.toMasterDTO(newUser);
        userMasterDTO.setRole(newUser.getRoles().stream()
                .map(role -> role.getName()) // Giả sử Role có phương thức getName()
                .collect(Collectors.toSet()));

        return userMasterDTO;

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
        if (userEditDTO.getRoleId() != null) {
            var roles = roleRepository.findById(userEditDTO.getRoleId());
            if (roles.isPresent()) {
                user.setRoles(Collections.singleton(roles.get()));
            }
        }

        user = userRepository.save(user);

        UserMasterDTO userMasterDTO = userMapper.toMasterDTO(user);
        userMasterDTO.setRole(user.getRoles().stream()
                .map(role -> role.getName()) // Giả sử Role có phương thức getName()
                .collect(Collectors.toSet()));

        return userMasterDTO;
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
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                Set<String> roleNames = user.getRoles().stream()
                        .map(role -> role.getName()) // Giả sử Role có phương thức getName()
                        .collect(Collectors.toSet());
                userDTO.setRole(roleNames);
            }
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
