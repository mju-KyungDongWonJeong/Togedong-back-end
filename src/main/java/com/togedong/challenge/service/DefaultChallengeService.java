package com.togedong.challenge.service;

import static com.togedong.global.exception.ErrorCode.CHALLENGE_NOT_FOUND;

import com.togedong.challenge.entity.Challenge;
import com.togedong.challenge.repository.ChallengeRepository;
import com.togedong.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultChallengeService implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Override
    public Challenge findChallengeByName(final String name) {
        return challengeRepository.findById(name)
            .orElseThrow(() -> new CustomException(CHALLENGE_NOT_FOUND));
    }

    @Override
    public List<Challenge> findAllChallenges() {
        return challengeRepository.findAll();
    }
}
