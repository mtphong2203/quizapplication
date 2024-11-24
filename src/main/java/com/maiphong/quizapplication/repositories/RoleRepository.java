package com.maiphong.quizapplication.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maiphong.quizapplication.entities.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {

}
