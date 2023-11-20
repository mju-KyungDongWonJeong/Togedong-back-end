package com.togedong.challenge.service;

import static com.togedong.global.exception.ErrorCode.CHALLENGE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.togedong.challenge.repository.ChallengeRepository;
import com.togedong.global.exception.CustomException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultChallengeServiceTest {

    @InjectMocks
    private DefaultChallengeService defaultChallengeService;

    @Mock
    private ChallengeRepository challengeRepository;

    @Test
    void 없는_챌린지_이름으로_검색_불가_테스트() {
        // given
        String unknownName = "unKnown";
        given(challengeRepository.findById(any()))
            .willReturn(Optional.empty());

        // when & then
        assertAll(() -> {
            CustomException exception = assertThrows(CustomException.class, () ->
                defaultChallengeService.findChallengeByName(unknownName));
            assertEquals(CHALLENGE_NOT_FOUND, exception.getErrorCode());
        });
    }
}
