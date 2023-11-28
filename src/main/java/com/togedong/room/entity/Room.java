package com.togedong.room.entity;

import com.togedong.record.Exercise;
import com.togedong.room.controller.dto.RoomCreateRequest;
import com.togedong.room.controller.dto.RoomCreateResponse;
import com.togedong.room.controller.dto.RoomResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class Room {

    @Id
    private String id;

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

    public static Room of(final RoomCreateRequest request, final String sessionId,
        final String managerName, final Exercise exercise) {
        return new Room(sessionId, managerName, request.title(),
            request.memberLimit(), exercise,
            request.password(),
            request.hasPassword());
    }

    public RoomCreateResponse toCreateDto(final String connectionToken) {
        return new RoomCreateResponse(id, title, managerName, memberLimit, connectionToken,
            exercise.getName());
    }

    public RoomResponse toCommonDto() {
        return new RoomResponse(id, title, managerName, memberLimit, exercise.getName(),
            hasPassword);
    }

    public boolean canJoin(final String password) {
        if (!hasPassword) {
            return true;
        }
        return this.password.equals(password);
    }
}
