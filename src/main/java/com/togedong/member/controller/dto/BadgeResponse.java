package com.togedong.member.controller.dto;

import java.util.List;

public record BadgeResponse(int badgeCount, int badgePercent, List<String> badges) {

}
