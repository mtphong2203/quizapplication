package com.maiphong.quizapplication.entities;

import java.time.ZonedDateTime;
import java.util.Set;

import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserQuiz extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userQuiz")
    private Set<UserAnswer> userAnswers;

    private String quizCode;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(columnDefinition = "DATETIMEOFFSET", nullable = false)
    private ZonedDateTime startDate;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(columnDefinition = "DATETIMEOFFSET")
    private ZonedDateTime endDate;

}
