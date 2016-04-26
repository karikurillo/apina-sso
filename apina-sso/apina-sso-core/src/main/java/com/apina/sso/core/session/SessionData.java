package com.apina.sso.core.session;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SessionData {
    private String token;
    private Map<String, String> attributes;
    private Long sessionStart;

    public SessionData(String sessionToken, Map<String, String> sessionAttributes) {
        this.token = sessionToken;
        this.sessionStart = System.currentTimeMillis();
        this.attributes = new HashMap<String, String>();
        this.setAttributes(sessionAttributes);
    }

    public void setAttributes(Map<String, String> sessionAttributes) {
        if (sessionAttributes != null) {
            for (Map.Entry<String, String> entry : sessionAttributes.entrySet()) {
                this.attributes.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
