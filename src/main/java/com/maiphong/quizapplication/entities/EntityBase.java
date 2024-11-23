package com.maiphong.quizapplication.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityBase {

    @Column(name = "create_at", unique = true, nullable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at", unique = true)
    private LocalDateTime updateAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
