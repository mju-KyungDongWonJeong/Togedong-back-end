package com.togedong.challenge.service;

import com.togedong.challenge.entity.Challenge;
import java.util.List;

public interface ChallengeService {

    Challenge findChallengeByName(final String name);

    List<Challenge> findAllChallenges();
}
