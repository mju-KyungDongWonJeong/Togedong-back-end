package com.togedong.member.controller.dto;

import java.util.List;

public record TotalChallengeResponse(List<ChallengeResponse> challenges, boolean isMine) {

}
