package com.togedong.record;

import lombok.Getter;

@Getter
public enum Exercise {

    PUSH_UP("푸시업"),
    SQUAT("스쿼트");

    private final String name;

    Exercise(final String name) {
        this.name = name;
    }
}
