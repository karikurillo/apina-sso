package com.apina.sso.core.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 */
@Component
@Scope("singleton")
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    public String createSession(String realm, String username, Map<String, String> sessionAttributes) throws Exception {
        // Generate id token for the session
        String token = generateToken();

        // Create session data item

        // Add session to cache

        return token;
    }

    public String generateToken() {
        return "token" + System.nanoTime();
    }
}
