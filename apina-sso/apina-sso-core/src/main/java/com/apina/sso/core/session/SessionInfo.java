package com.apina.sso.core.session;

/**
 * Created by Kari Kurillo on 9.5.2016.
 */
public class SessionInfo {
    private String username;
    private String realm;
    private String token;
    boolean sessionValid = false;

    public SessionInfo() {}

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
}
