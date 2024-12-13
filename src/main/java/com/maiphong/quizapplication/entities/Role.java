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

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
