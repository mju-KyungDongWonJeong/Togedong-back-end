package com.togedong.member.controller.dto;

import java.util.List;

public record DashBoardResponse(String userName, BadgeResponse badgeResponse, List<SimpleRecordResponse> bestRecords, boolean isMine) {

}
