package com.apina.sso.core.session;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SessionData {
    private String token;
    private String username;
    private String realm;
    private Map<String, String> attributes;
    private Long sessionStart;

    public SessionData(String realm, String username, String sessionToken, Map<String, String> sessionAttributes) {
        this.realm = realm;
        this.username = username;
        this.token = sessionToken;
        this.sessionStart = System.currentTimeMillis();
        this.attributes = new HashMap<String, String>();
        this.setAttributes(sessionAttributes);
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRealm() {
        return realm;
    }

    public Long getSessionStart() {
        return sessionStart;
    }

    public void setAttributes(Map<String, String> sessionAttributes) {
        if (sessionAttributes != null) {
            for (Map.Entry<String, String> entry : sessionAttributes.entrySet()) {
                this.attributes.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
