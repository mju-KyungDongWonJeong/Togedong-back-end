package com.togedong.challenge;

import static com.togedong.badge.Badge.PUSH_UP_BADGE;
import static com.togedong.badge.Badge.SQUAT_BADGE;
import static com.togedong.record.Exercise.PUSH_UP;
import static com.togedong.record.Exercise.SQUAT;

import com.togedong.badge.Badge;
import com.togedong.record.Exercise;
import java.util.function.Predicate;
import lombok.Getter;

@Getter
public enum Challenge {

    PUSH_UP_CHALLENGE(PUSH_UP, String.format(Consts.COUNT_CHALLENGE_DESCRIPTION, PUSH_UP.getName(),
        Consts.COUNT_CHALLENGE_CONDITION), PUSH_UP_BADGE, Challenge::countCheck
    ),
    SQUAT_CHALLENGE(SQUAT, String.format(Consts.COUNT_CHALLENGE_DESCRIPTION, SQUAT.getName(),
        Consts.COUNT_CHALLENGE_CONDITION), SQUAT_BADGE, Challenge::countCheck
    );

    private static final class Consts {

        private static final int COUNT_CHALLENGE_CONDITION = 1000;
        private static final String COUNT_CHALLENGE_DESCRIPTION = "%s %d개 달성!";
    }

    private final Exercise exercise;
    private final String description;
    private final Predicate<Integer> isComplete;
    private final Badge badge;

    Challenge(final Exercise exercise, final String description, final Badge badge,
        final Predicate<Integer> isComplete) {
        this.description = description;
        this.exercise = exercise;
        this.isComplete = isComplete;
        this.badge = badge;
    }

    public boolean isComplete(final int bestRecord) {
        return this.isComplete(bestRecord);
    }

    private static boolean countCheck(final int record) {
        return record >= Consts.COUNT_CHALLENGE_CONDITION;
    }
}
