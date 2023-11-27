package com.togedong.badge;

import lombok.Getter;

@Getter
public enum Badge {

    PUSH_UP_BADGE("push up image url"),

    SQUAT_BADGE("push up image url");

    static final int PERCENT_MULTIPLIER = 100;

    private final String imageUrl;

    Badge(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static int calculatePercent(final int count) {
        return count / Badge.values().length * PERCENT_MULTIPLIER;
    }
}
