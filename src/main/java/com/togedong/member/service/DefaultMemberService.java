package com.togedong.member.service;

import static com.togedong.global.exception.ErrorCode.USER_NOT_FOUND;
import static com.togedong.participant.ParticipantStatus.NOT_PARTICIPANT;
import static com.togedong.participant.ParticipantStatus.PARTICIPANT;

import com.togedong.member.controller.dto.ChallengeResponse;
import com.togedong.challenge.entity.Challenge;
import com.togedong.member.controller.dto.RecordResponse;
import com.togedong.member.controller.dto.TotalChallengeResponse;
import com.togedong.challenge.service.ChallengeService;
import com.togedong.participant.service.ParticipantService;
import com.togedong.global.exception.CustomException;
import com.togedong.global.exception.ErrorCode;
import com.togedong.record.Exercise;
import com.togedong.member.controller.dto.DashBoardResponse;
import com.togedong.member.controller.dto.SimpleRecordResponse;
import com.togedong.member.entity.Member;
import com.togedong.member.repository.MemberRepository;
import com.togedong.record.service.RecordService;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultMemberService implements MemberService {

    private final MemberRepository userRepository;
    private final ParticipantService participantService;
    private final ChallengeService challengeService;
    private final RecordService recordService;

    @Override
    public DashBoardResponse getUserDashBoard(final String userName, final Member member) {
        Member targetMember = findMemberByName(userName);

        List<SimpleRecordResponse> maxRecords = Stream.of(Exercise.values())
            .map(exercise -> new SimpleRecordResponse(exercise.getName(),
                member.getMaxRecord(exercise)))
            .toList();

        return new DashBoardResponse(userName, targetMember.getBadgeCount(), maxRecords,
            requestHimSelf(userName, member)
        );
    }

    @Override
    @Transactional
    public void participantChallenge(final Member member, final String userName,
        final String challengeName) {
        Member targetMember = findMemberByName(userName);

        if (!requestHimSelf(userName, member)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        log.info("Member : " + targetMember.getUserName() + " ChallengeName : " + challengeName);
        participantService.participantChallenge(targetMember, challengeName);
    }

    @Override
    public TotalChallengeResponse getMemberChallenges(final Member member, final String userName) {
        Member targetMember = findMemberByName(userName);

        List<Challenge> allChallenges = challengeService.findAllChallenges();
        List<ChallengeResponse> challenges = allChallenges.stream()
            .map(challenge -> includeMemberInfo(challenge, targetMember))
            .toList();

        return new TotalChallengeResponse(challenges, requestHimSelf(userName, member));
    }

    @Override
    public List<RecordResponse> getExerciseRank(final String exerciseName) {
        Exercise exercise = Exercise.findExerciseByName(exerciseName);
        return recordService.getRankByExercise(exercise);
    }

    @Override
    public boolean requestHimSelf(final String userName, final Member member) {
        return member.hasSameName(userName);
    }

    private ChallengeResponse includeMemberInfo(final Challenge challenge, final Member member) {

        int participantCount = participantService.getChallengeParticipantCount(challenge.getId());
        String description = challenge.getDescription();

        log.info("participant count : " + participantCount);

        if (!participantService.alreadyParticipate(member, challenge)) {
            return new ChallengeResponse(description, participantCount, 0,
                NOT_PARTICIPANT);
        }

        int progressPercent = challenge.calculateProgressPercent(
            member.calculateRecordsSum(challenge.getExercise()));
        return new ChallengeResponse(description, participantCount, progressPercent, PARTICIPANT);
    }

    private Member findMemberByName(final String name) {
        return userRepository.findByUserName(name)
            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
