package com.togedong.badge;

import lombok.Getter;

@Getter
public enum Badge {

    PUSH_UP_BADGE("push up image url"),
    SQUAT_BADGE("push up image url");

    private final String imageUrl;

    Badge(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
