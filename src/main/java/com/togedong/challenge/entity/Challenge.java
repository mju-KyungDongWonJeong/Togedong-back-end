package com.togedong.challenge.entity;

import com.togedong.badge.Badge;
import com.togedong.record.Exercise;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Challenge {

    private static final int COUNT_CHALLENGE_CONDITION = 1000;

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private Exercise exercise;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private Badge badge;

    Challenge(final Exercise exercise, final String description, final Badge badge) {
        this.description = description;
        this.exercise = exercise;
        this.badge = badge;
    }

    public int calculateProgressPercent(final int sum) {
        return sum / COUNT_CHALLENGE_CONDITION * 100;
    }
}
