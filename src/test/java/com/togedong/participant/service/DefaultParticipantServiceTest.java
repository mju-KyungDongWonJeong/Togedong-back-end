package com.togedong.participant.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.togedong.challenge.service.ChallengeService;
import com.togedong.global.exception.CustomException;
import com.togedong.global.exception.ErrorCode;
import com.togedong.member.entity.Member;
import com.togedong.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultParticipantServiceTest {

    @InjectMocks
    DefaultParticipantService participantService;

    @Mock
    ParticipantRepository participantRepository;

    @Mock
    ChallengeService challengeService;

    @Test
    void 이미_신청한_챌린지일_경우_예외_테스트() {
        // given
        Member testMember = Member.builder()
            .userId("test")
            .build();

        BDDMockito.given(participantService.alreadyParticipate(any(), any()))
            .willReturn(true);

        // when & then
        assertAll(() -> {
            CustomException exception = assertThrows(CustomException.class, () ->
                participantService.participantChallenge(testMember, "test"));
            assertEquals(ErrorCode.DUPLICATE_CHALLENGE, exception.getErrorCode());
        });
    }
}
