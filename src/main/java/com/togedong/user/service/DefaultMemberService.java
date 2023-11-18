package com.togedong.user.service;

import static com.togedong.global.exception.ErrorCode.USER_NOT_FOUND;

import com.togedong.global.exception.CustomException;
import com.togedong.record.Exercise;
import com.togedong.user.controller.dto.DashBoardResponse;
import com.togedong.user.controller.dto.SimpleRecordResponse;
import com.togedong.user.entity.Member;
import com.togedong.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final UserRepository userRepository;

    @Override
    public DashBoardResponse getUserDashBoard(final String userName, final Member member) {
        Member targetMember = userRepository.findByUserName(userName)
            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<SimpleRecordResponse> maxRecords = Stream.of(Exercise.values())
            .map(exercise -> new SimpleRecordResponse(exercise.getName(),
                member.getMaxRecord(exercise)))
            .toList();

        return new DashBoardResponse(userName, targetMember.getBadgeCount(), maxRecords,
            requestHimSelf(userName, member)
        );
    }

    @Override
    public boolean requestHimSelf(final String userName, final Member member) {
        return member.hasSameName(userName);
    }
}
