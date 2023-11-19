package com.togedong.participant;

import lombok.Getter;

@Getter
public enum ParticipantStatus {

    PARTICIPANT(true),
    NOT_PARTICIPANT(false);

    private final boolean isParticipant;

    ParticipantStatus(final boolean isParticipant) {
        this.isParticipant = isParticipant;
    }
}
