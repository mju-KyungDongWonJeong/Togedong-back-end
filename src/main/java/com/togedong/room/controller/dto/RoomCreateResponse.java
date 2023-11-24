package com.togedong.room.controller.dto;

public record RoomCreateResponse(String roomId, String roomTitle, String roomManager,
                                 int memberLimit, String connectionToken, String exerciseName) {

}
