package com.togedong.record.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RecordRequest(@NotNull String exerciseName, @NotNull @Min(0) int record) {

}
