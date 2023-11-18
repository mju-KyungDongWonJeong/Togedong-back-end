package com.togedong.auth.service;

import static com.togedong.global.exception.ErrorCode.USER_NOT_FOUND;
import static com.togedong.global.exception.ErrorCode.WRONG_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.togedong.auth.dto.SignInRequest;
import com.togedong.global.exception.CustomException;
import com.togedong.global.helper.JwtProvider;
import com.togedong.member.entity.Member;
import com.togedong.member.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtAuthServiceTest {

    @InjectMocks
    private JwtAuthService authService;

    @Mock
    private MemberRepository userRepository;
    @Mock
    private JwtProvider jwtProvider;

    @Test
    void 없는_아이디로_로그인시_에러() {
        // given
        SignInRequest request = new SignInRequest("choi", "test");

        // when & then
        assertAll(() -> {
            CustomException exception = assertThrows(CustomException.class,
                () -> authService.login(request));
            assertEquals(exception.getErrorCode(), USER_NOT_FOUND);
        });
    }

    @Test
    void 비밀번호_불일치_에러() {
        // given
        Member member = Member.builder()
            .userId("choi")
            .password("test1")
            .userName("choi")
            .build();

        SignInRequest request = new SignInRequest("choi", "test");
        given(userRepository.findByUserId(anyString()))
            .willReturn(Optional.of(member));

        // when & then
        assertAll(() -> {
            CustomException exception = assertThrows(CustomException.class,
                () -> authService.login(request));
            assertEquals(exception.getErrorCode(), WRONG_PASSWORD);
        });
    }

    @Test
    void 유효하지_않은_페이로드_에러() {
        // given
        String invalidPayLoad = "invalidPayload";

        // when & then
        assertAll(() -> {
            CustomException exception = assertThrows(CustomException.class,
                () -> authService.findMemberByJwt(invalidPayLoad));
            assertEquals(exception.getErrorCode(), USER_NOT_FOUND);
        });
    }
}
