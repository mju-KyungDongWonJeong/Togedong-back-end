package com.togedong.participant.repository;

import com.togedong.challenge.entity.Challenge;
import com.togedong.participant.entity.Participant;
import com.togedong.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    boolean existsParticipantByMemberAndChallenge(final Member member, final Challenge challenge);

    int countByChallenge_Id(final String challengeId);
}
