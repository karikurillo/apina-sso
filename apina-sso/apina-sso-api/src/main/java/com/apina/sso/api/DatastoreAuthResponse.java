package com.apina.sso.api;

import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class DatastoreAuthResponse {
    private DatastoreAuthStatus datastoreAuthStatus;
    private Map<String, String> responseAttributes;
    private Map<String, String> sessionAttributes;

    public DatastoreAuthResponse() {

    }

    public DatastoreAuthResponse(DatastoreAuthStatus datastoreAuthStatus) {
        this.datastoreAuthStatus = datastoreAuthStatus;
        this.responseAttributes = Collections.EMPTY_MAP;
    }

    public DatastoreAuthResponse(DatastoreAuthStatus datastoreAuthStatus, Map<String, String> responseAttributes) {
        this(datastoreAuthStatus, responseAttributes, null);
    }

    public DatastoreAuthResponse(DatastoreAuthStatus datastoreAuthStatus, Map<String, String> responseAttributes, Map<String, String> sessionAttributes) {
        this.datastoreAuthStatus = datastoreAuthStatus;
        this.responseAttributes = responseAttributes;
        this.sessionAttributes = sessionAttributes;
    }

    public DatastoreAuthStatus getDatastoreAuthStatus() {
        return datastoreAuthStatus;
    }

    public Map<String, String> getResponseAttributes() {
        return responseAttributes;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }
}
