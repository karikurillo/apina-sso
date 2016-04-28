package com.apina.sso.core.services.v1.pojos;

/**
 * Created by Kari Kurillo on 28/04/16.
 */
public class RestResponseLogout {
    private boolean logout;

    public RestResponseLogout(boolean logout) {
        this.logout = logout;
    }

    public boolean getLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }
}
