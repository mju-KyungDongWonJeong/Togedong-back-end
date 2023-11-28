package com.togedong.room.controller.dto;

public record RoomResponse(String roomId, String title, String managerName, int memberLimit,
                           String exerciseName,
                           boolean hasPassword) {

}
