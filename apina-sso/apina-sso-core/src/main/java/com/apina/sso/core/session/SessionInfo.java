package com.apina.sso.core.session;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Kari Kurillo on 9.5.2016.
 */
public class SessionInfo {
    private String username;
    private String realm;
    private String token;
    boolean sessionValid = false;
    private Map<String, String> sessionAttributes;

    public SessionInfo() {
        this.sessionAttributes = Collections.EMPTY_MAP;
    }

    public SessionInfo(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSessionValid() {
        return sessionValid;
    }

    public void setSessionValid(boolean sessionValid) {
        this.sessionValid = sessionValid;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Map<String, String> attributes) {
        this.sessionAttributes = attributes;
    }
}
