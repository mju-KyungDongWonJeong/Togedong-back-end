package com.togedong.badge;

import lombok.Getter;

@Getter
public enum Badge {

    PUSH_UP_BADGE,

    SQUAT_BADGE;

    static final int PERCENT_MULTIPLIER = 100;

    public static int calculatePercent(final int count) {
        return count / Badge.values().length * PERCENT_MULTIPLIER;
    }
}
