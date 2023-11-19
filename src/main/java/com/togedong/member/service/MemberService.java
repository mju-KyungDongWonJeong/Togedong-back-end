package com.togedong.member.service;

import com.togedong.member.controller.dto.TotalChallengeResponse;
import com.togedong.member.controller.dto.DashBoardResponse;
import com.togedong.member.entity.Member;

public interface MemberService {

    DashBoardResponse getUserDashBoard(final String userName, final Member member);

    boolean requestHimSelf(final String userName, final Member member);

    void participantChallenge(final Member member, final String userName,
        final String challengeName);

    TotalChallengeResponse getMemberChallenges(final Member member, final String userName);
}
