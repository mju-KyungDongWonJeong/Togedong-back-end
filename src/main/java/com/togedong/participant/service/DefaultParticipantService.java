package com.togedong.participant.service;

import static com.togedong.global.exception.ErrorCode.DUPLICATE_CHALLENGE;

import com.togedong.challenge.entity.Challenge;
import com.togedong.challenge.service.ChallengeService;
import com.togedong.global.exception.CustomException;
import com.togedong.member.entity.Member;
import com.togedong.participant.entity.Participant;
import com.togedong.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultParticipantService implements ParticipantService {

    private static final int ARCHIVE_CHALLENGE_PERCENT = 100;

    private final ParticipantRepository participantRepository;
    private final ChallengeService challengeService;

    @Override
    @Transactional
    public void participantChallenge(final Member member, final String challengeName) {
        Challenge challenge = challengeService.findChallengeByName(challengeName);

        if (alreadyParticipate(member, challenge)) {
            throw new CustomException(DUPLICATE_CHALLENGE);
        }

        Participant participant = Participant.participateChallenge(member, challenge);
        participantRepository.save(participant);
    }

    @Override
    public boolean alreadyParticipate(final Member member, final Challenge challenge) {
        return participantRepository.existsParticipantByMemberAndChallenge(member, challenge);
    }

    @Override
    public int getChallengeParticipantCount(final String challengeName) {
        return participantRepository.countByChallenge_Id(challengeName);
    }

    @Override
    @Transactional
    public void checkParticipantArchive(final Member member) {
        member.getParticipants()
            .stream()
            .map(Participant::getChallenge)
            .filter(challenge -> challenge.calculateProgressPercent(
                member.calculateRecordsSum(challenge.getExercise())) >= ARCHIVE_CHALLENGE_PERCENT)
            .forEach(challenge -> challenge.giveArchiveBadge(member));
    }
}
