package com.togedong.room.service.domain;

import com.togedong.global.exception.CustomException;
import com.togedong.global.exception.ErrorCode;
import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenViduManager {

    private static final String OPENVIDU_URL = "https://43.202.202.99/";
    private static final String OPENVIDU_SECRET = "homedong";

    private final OpenVidu openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);

    public String initializeSession() {
        SessionProperties properties = SessionProperties.fromJson(new HashMap<>()).build();

        try {
            Session session = openvidu.createSession(properties);
            return session.getSessionId();
        } catch (OpenViduJavaClientException | OpenViduHttpException exception) {
            throw new CustomException(ErrorCode.OPENVIDU_ERROR);
        }
    }

    public String createConnection(final String sessionId) {

        Session session = openvidu.getActiveSession(sessionId);

        if (session == null) {
            log.error("session " + sessionId + " not found");
            throw new CustomException(ErrorCode.SESSION_NOT_FOUND);
        }

        try {
            ConnectionProperties properties = ConnectionProperties.fromJson(new HashMap<>())
                .build();
            Connection connection = session.createConnection(properties);
            log.info("token " + connection.getToken() + " created");
            return connection.getToken();
        } catch (OpenViduJavaClientException | OpenViduHttpException exception) {
            throw new CustomException(ErrorCode.OPENVIDU_ERROR);
        }
    }
}
