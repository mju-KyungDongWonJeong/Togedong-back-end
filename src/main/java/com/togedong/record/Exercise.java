package com.togedong.record;

import static com.togedong.global.exception.ErrorCode.EXERCISE_NOT_FOUND;

import com.togedong.global.exception.CustomException;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Exercise {

    PUSH_UP("푸쉬업"),
    SQUAT("스쿼트");

    private final String name;

    Exercise(final String name) {
        this.name = name;
    }

    public static Exercise findExerciseByName(final String name) {
        return Stream.of(Exercise.values())
            .filter(exercise -> exercise.name().equals(name))
            .findAny()
            .orElseThrow(()->new CustomException(EXERCISE_NOT_FOUND));
    }
}
