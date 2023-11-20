package com.togedong.auth.service;

import static com.togedong.global.exception.ErrorCode.DUPLICATE_USER_ID;
import static com.togedong.global.exception.ErrorCode.USER_NOT_FOUND;
import static com.togedong.global.exception.ErrorCode.WRONG_PASSWORD;

import com.togedong.auth.controller.dto.SignInRequest;
import com.togedong.auth.controller.dto.SignUpRequest;
import com.togedong.auth.controller.dto.TokenResponse;
import com.togedong.auth.controller.dto.UserResponse;
import com.togedong.global.exception.CustomException;
import com.togedong.global.helper.JwtProvider;
import com.togedong.member.entity.Member;
import com.togedong.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtAuthService implements AuthService {

    private final MemberRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public UserResponse register(final SignUpRequest request) {
        validateDuplicateUserId(request.userId());

        Member member = Member.builder()
            .userId(request.userId())
            .userName(request.userName())
            .password(request.password())
            .build();

        return userRepository.save(member)
            .toDto();
    }

    @Override
    @Transactional
    public TokenResponse login(final SignInRequest request) {
        Member member = findUserByUserId(request);

        if (!member.hasSamePassword(request.password())) {
            throw new CustomException(WRONG_PASSWORD);
        }

        String token = jwtProvider.createAccessToken(request.userId());
        return new TokenResponse(token);
    }

    @Override
    public Member findMemberByJwt(final String jwt) {
        String memberFormId = jwtProvider.getPayload(jwt);

        return userRepository.findByUserId(memberFormId)
            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private Member findUserByUserId(final SignInRequest request) {
        return userRepository.findByUserId(request.userId())
            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private void validateDuplicateUserId(final String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new CustomException(DUPLICATE_USER_ID);
        }
    }
}
