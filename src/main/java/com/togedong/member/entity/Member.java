package com.togedong.member.entity;

import com.togedong.auth.controller.dto.UserResponse;
import com.togedong.badge.Badge;
import com.togedong.participant.entity.Participant;
import com.togedong.global.exception.CustomException;
import com.togedong.global.exception.ErrorCode;
import com.togedong.record.Exercise;
import com.togedong.record.entity.ExerciseRecord;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Member {

    private static final int NOT_FOUND_RECORD_COUNT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String userId;

    private String password;

    @ElementCollection(targetClass = Badge.class)
    @CollectionTable(name = "badge")
    @Column(name = "badge")
    @Enumerated(EnumType.STRING)
    private List<Badge> badges;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ExerciseRecord> records;

    public boolean hasSamePassword(String password) {
        return password.equals(this.password);
    }

    public UserResponse toDto() {
        return new UserResponse(userId, userName);
    }

    public List<ExerciseRecord> findExerciseRecordsByKind(final Exercise exercise) {
        log.info(userName + "의 기록 개수 : " + records.size());
        return records.stream()
            .filter(record -> record.isSameExercise(exercise))
            .toList();
    }

    public int calculateRecordsSum(final Exercise exercise) {
        int sum = findExerciseRecordsByKind(exercise).stream()
            .mapToInt(ExerciseRecord::getRecord)
            .sum();
        log.info(userName + "의 기록 총 개수 : " + sum);
        return sum;
    }

    public int getMaxRecord(final Exercise exercise) {
        return findExerciseRecordsByKind(exercise).stream()
            .mapToInt(ExerciseRecord::getRecord)
            .max()
            .orElseGet(() -> NOT_FOUND_RECORD_COUNT);
    }

    public boolean hasSameName(final String userName) {
        return this.userName.equals(userName);
    }

    public int getBadgeCount() {
        return badges.size();
    }

    public boolean appliedChallenge(final Participant participant) {
        return participants.contains(participant);
    }

    public void participantChallenge(final Participant participant) {
        if (appliedChallenge(participant)) {
            throw new CustomException(ErrorCode.DUPLICATE_CHALLENGE);
        }

        participants.add(participant);
    }

    public void addRecord(final ExerciseRecord exerciseRecord) {
        records.add(exerciseRecord);
    }

    public void addBadge(final Badge badge) {
        badges.add(badge);
    }

    public Map<Badge, Boolean> getBadgeStatus() {
        Map<Badge, Boolean> badgeStatus = new EnumMap<>(Badge.class);
        Stream.of(Badge.values())
            .forEach(badge -> badgeStatus.put(badge, Boolean.FALSE));
        badges.forEach(badge -> badgeStatus.put(badge, Boolean.TRUE));
        return badgeStatus;
    }

    @Builder
    public Member(final String userId, final String password, final String userName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.badges = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", badges=" + badges +
            ", participants=" + participants +
            ", records=" + records +
            '}';
    }
}
