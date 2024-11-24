package com.maiphong.quizapplication.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "avatar")
    private String thumbnailUrl;

    @Column(name = "password")
    private String password;

    @ManyToMany(mappedBy = "users")
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<UserQuiz> users;
}
