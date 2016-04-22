package com.apina.sso.api;

import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class DatastoreAuthenticationResponse {
    protected AuthenticationEnum status;
    protected Map<String, String> responseAttributes;
    protected Map<String, String> sessionAttributes;

    public DatastoreAuthenticationResponse() {

    }

    public DatastoreAuthenticationResponse(AuthenticationEnum status) {
        this.status = status;
        this.responseAttributes = Collections.EMPTY_MAP;
    }

    public DatastoreAuthenticationResponse(AuthenticationEnum status, Map<String, String> responseAttributes) {
        this(status, responseAttributes, null);
    }

    public DatastoreAuthenticationResponse(AuthenticationEnum status, Map<String, String> responseAttributes, Map<String, String> sessionAttributes) {
        this.status = status;
        this.responseAttributes = responseAttributes;
        this.sessionAttributes = sessionAttributes;
    }
}
