package com.apina.sso.api;

import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class AuthenticationResponse {
    protected AuthenticationEnum status;
    protected Map<String, String> responseAttributes;

    public AuthenticationResponse() {

    }

    public AuthenticationResponse(AuthenticationEnum status) {
        this.status = status;
        this.responseAttributes = Collections.EMPTY_MAP;
    }

    public AuthenticationResponse(AuthenticationEnum status, Map<String, String> responseAttributes) {
        this.status = status;
        this.responseAttributes = responseAttributes;
    }
}
