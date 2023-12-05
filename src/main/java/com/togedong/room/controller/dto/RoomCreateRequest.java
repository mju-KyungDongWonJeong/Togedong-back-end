package com.togedong.room.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RoomCreateRequest(@NotNull String title, @NotNull @Min(1) int memberLimit,
                                @NotNull String exerciseName, @NotNull boolean hasPassword,
                                String password) {

}
