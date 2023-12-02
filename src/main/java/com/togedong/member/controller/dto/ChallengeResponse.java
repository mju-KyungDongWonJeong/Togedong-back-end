package com.togedong.member.controller.dto;

import com.togedong.participant.ParticipantStatus;

public record ChallengeResponse(String challengeId, String description, int participantCount, int progressPercent,
                                ParticipantStatus isParticipating
) {

}
