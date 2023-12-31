package com.togedong.record.entity;

import com.togedong.member.controller.dto.RecordResponse;
import com.togedong.record.Exercise;
import com.togedong.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private Integer record;

    @CreatedDate
    private LocalDateTime createdAt;

    public ExerciseRecord(final Exercise exercise, final Member member, final Integer record) {
        this.exercise = exercise;
        this.member = member;
        this.record = record;
    }

    public boolean isSameExercise(final Exercise exercise) {
        return this.exercise.equals(exercise);
    }

    public RecordResponse toDto() {
        return new RecordResponse(member.getUserName(), record);
    }
}
