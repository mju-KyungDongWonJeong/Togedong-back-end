package com.togedong.participant.service;

import com.togedong.challenge.entity.Challenge;
import com.togedong.member.entity.Member;

public interface ParticipantService {

    void participantChallenge(final Member member, final String challenge);

    boolean alreadyParticipate(final Member member, final Challenge challenge);

    int getChallengeParticipantCount(final String challengeName);

    void checkParticipantArchive(final Member member);
}
