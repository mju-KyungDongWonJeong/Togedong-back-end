package com.togedong.room.entity;

import com.togedong.record.Exercise;
import com.togedong.room.controller.dto.RoomCreateRequest;
import com.togedong.room.controller.dto.RoomCreateResponse;
import com.togedong.room.controller.dto.RoomResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String title;

    @Column
    private String managerName;

    @Column
    private int memberLimit;

    @Enumerated(value = EnumType.STRING)
    private Exercise exercise;

    @Column
    private String password;

    @Column
    private boolean hasPassword;

    private Room(final String title, final String managerName, final int memberLimit,
        final Exercise exercise, final String password, final boolean hasPassword) {
        this.title = title;
        this.managerName = managerName;
        this.memberLimit = memberLimit;
        this.exercise = exercise;
        this.password = password;
        this.hasPassword = hasPassword;
    }

    public static Room of(final RoomCreateRequest request,
        final String managerName, final Exercise exercise) {
        return new Room(request.title(), managerName,
            request.memberLimit(), exercise,
            request.password(),
            request.hasPassword());
    }

    public RoomCreateResponse toCreateDto() {
        return new RoomCreateResponse(title, managerName, memberLimit,
            exercise.name());
    }

    public RoomResponse toCommonDto() {
        return new RoomResponse(id, title, managerName, memberLimit, exercise.name(),
            hasPassword);
    }

    public boolean canJoin(final String password) {
        if (!hasPassword) {
            return true;
        }
        return this.password.equals(password);
    }
}
