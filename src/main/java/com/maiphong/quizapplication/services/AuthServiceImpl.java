package com.maiphong.quizapplication.services;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.auth.RegisterRequestDTO;
import com.maiphong.quizapplication.dtos.user.UserInformationDTO;
import com.maiphong.quizapplication.entities.User;
import com.maiphong.quizapplication.mappers.UserMapper;
import com.maiphong.quizapplication.repositories.UserRepository;

@Service
@Transactional
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(auth -> "ROLE_" + auth.getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);

    }

    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UUID register(RegisterRequestDTO registerDTO) {
        User existUser = userRepository.findByUsername(registerDTO.getUsername());

        if (existUser != null) {
            throw new IllegalArgumentException("User name is already exist!");
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password confirm do not match");
        }

        var user = userMapper.toEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        user = userRepository.save(user);

        return user.getId();

    }

    @Override
    public UserInformationDTO getUserInformation(String username) {
        var user = userRepository.findByUsername(username);
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setFirstName(user.getFirstName());
        userInformationDTO.setLastName(user.getLastName());
        userInformationDTO.setDisplayName(user.getDisplayName());
        userInformationDTO.setUsername(user.getUsername());
        userInformationDTO.setEmail(user.getEmail());
        userInformationDTO.setPhoneNumber(user.getPhoneNumber());
        userInformationDTO.setThumbnailUrl(user.getThumbnailUrl());
        var roles = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet());
        userInformationDTO.setRoles(roles);

        return userInformationDTO;
    }

}
