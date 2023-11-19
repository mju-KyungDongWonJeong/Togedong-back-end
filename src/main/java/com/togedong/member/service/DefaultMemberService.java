package com.togedong.member.service;

import static com.togedong.global.exception.ErrorCode.USER_NOT_FOUND;
import static com.togedong.participant.ParticipantStatus.NOT_PARTICIPANT;
import static com.togedong.participant.ParticipantStatus.PARTICIPANT;

import com.togedong.member.controller.dto.ChallengeResponse;
import com.togedong.challenge.entity.Challenge;
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
    public boolean requestHimSelf(final String userName, final Member member) {
        return member.hasSameName(userName);
    }
    private Member findMemberByName(final String name) {
        return userRepository.findByUserName(name)
            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
