package com.apina.sso.core.security;

import com.apina.sso.core.realm.RealmAuthResponse;

/**
 * Created by Kari Kurillo on 26.4.2016.
 */
public class AuthenticationResponse {
    private RealmAuthResponse realmAuthResponse;
    private String token;
    private boolean authenticated = false;

    public RealmAuthResponse getRealmAuthResponse() {
        return realmAuthResponse;
    }

    public void setRealmAuthResponse(RealmAuthResponse realmAuthResponse) {
        this.realmAuthResponse = realmAuthResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
