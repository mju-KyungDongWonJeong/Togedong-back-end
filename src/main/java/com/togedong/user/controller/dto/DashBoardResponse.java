package com.togedong.user.controller.dto;

import java.util.List;

public record DashBoardResponse(String userName, int badgeCount, List<SimpleRecordResponse> bestRecords, boolean isMine) {

}
