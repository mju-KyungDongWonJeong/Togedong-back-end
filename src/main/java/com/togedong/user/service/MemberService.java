package com.togedong.user.service;

import com.togedong.user.controller.dto.DashBoardResponse;
import com.togedong.user.entity.Member;

public interface MemberService {

    DashBoardResponse getUserDashBoard(final String userName, final Member member);

    boolean requestHimSelf(final String userName, final Member member);
}
