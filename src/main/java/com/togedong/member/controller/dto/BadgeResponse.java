package com.togedong.member.controller.dto;

import com.togedong.badge.Badge;
import java.util.Map;

public record BadgeResponse(int badgeCount, int badgePercent, Map<Badge,Boolean> badges) {

}
