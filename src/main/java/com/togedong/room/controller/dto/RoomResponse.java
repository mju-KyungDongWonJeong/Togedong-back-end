package com.togedong.room.controller.dto;

import java.util.UUID;

public record RoomResponse(UUID roomId, String title, String managerName, int memberLimit,
                           String exerciseName,
                           boolean hasPassword) {

}
