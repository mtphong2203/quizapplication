package com.maiphong.quizapplication.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends EntityMaster {

    @Column(columnDefinition = "NVARCHAR(30)", nullable = false)
    private String firstName;

    @Column(columnDefinition = "NVARCHAR(30)", nullable = false)
    private String lastName;

    @Column(columnDefinition = "NVARCHAR(30)", nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "NVARCHAR(50)", nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String thumbnailUrl;

    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<UserQuiz> userQuizs;

    @OneToMany(mappedBy = "user")
    private Set<UserAnswer> userAnswers;
}
