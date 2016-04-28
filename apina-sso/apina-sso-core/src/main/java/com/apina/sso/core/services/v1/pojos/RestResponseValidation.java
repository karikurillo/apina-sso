package com.apina.sso.core.services.v1.pojos;

/**
 * Created by Kari Kurillo on 28/04/16.
 */
public class RestResponseValidation {
    private boolean valid;

    public RestResponseValidation(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
