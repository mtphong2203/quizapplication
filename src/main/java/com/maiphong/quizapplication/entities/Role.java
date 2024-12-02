package com.maiphong.quizapplication.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends EntityMaster {

    @Column(columnDefinition = "NVARCHAR(50)", unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
