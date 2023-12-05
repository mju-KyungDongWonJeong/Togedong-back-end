package com.togedong.room.controller.dto;

public record RoomCreateResponse(String roomTitle, String roomManager,
                                 int memberLimit, String exerciseName) {

}
