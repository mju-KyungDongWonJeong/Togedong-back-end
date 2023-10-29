package com.togedong.global.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    private static final String MESSAGE_KEY = "message";
    private static final String STATUS_KEY = "status";
    private static final String DATA_KEY = "data";


    public static ResponseEntity<Object> generateResponse(final String message,
        final HttpStatus status,
        final Object data) {
        Map<String, Object> map = new HashMap<>();

        map.put(MESSAGE_KEY, message);
        map.put(STATUS_KEY, status);
        map.put(DATA_KEY, data);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponseWithoutMessage(final HttpStatus status,
        final Object data) {
        Map<String, Object> map = new HashMap<>();

        map.put(STATUS_KEY, status);
        map.put(DATA_KEY, data);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponseWithoutData(final HttpStatus status,
        final String message) {
        Map<String, Object> map = new HashMap<>();

        map.put(STATUS_KEY, status);
        map.put(MESSAGE_KEY, message);

        return new ResponseEntity<>(map, status);
    }
}
