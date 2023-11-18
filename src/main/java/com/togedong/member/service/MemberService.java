package com.togedong.member.service;

import com.togedong.member.controller.dto.DashBoardResponse;
import com.togedong.member.entity.Member;

public interface MemberService {

    DashBoardResponse getUserDashBoard(final String userName, final Member member);

    boolean requestHimSelf(final String userName, final Member member);
}
