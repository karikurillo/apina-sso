package com.apina.sso.core.realm;

import com.apina.sso.api.DatastoreAuthResponse;

import java.util.Map;

/**
 * Created by Kari Kurillo on 23/04/16.
 */
public class RealmAuthResponse {
    private RealmAuthStatus realmAuthStatus;
    private String realm;
    private String datastoreName;
    private String datastoreId;
    // Returned with authentication response
    private Map<String, String> responseAttributes;
    // Put to session and available for datastores
    private Map<String, String> sessionAttributes;

    public RealmAuthResponse(String realm) {
        this(realm, RealmAuthStatus.USER_NOT_FOUND);
    }
    public RealmAuthResponse(String realm, RealmAuthStatus status) {
        this.realm = realm;
        this.realmAuthStatus = status;
    }

    public void setRealmAuthStatus(RealmAuthStatus status) {
        this.realmAuthStatus = status;
    }

    public RealmAuthStatus getRealmAuthStatus() {
        return this.realmAuthStatus;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getDatastoreId() {
        return datastoreId;
    }

    public void setDatastoreId(String datastoreId) {
        this.datastoreId = datastoreId;
    }

    public Map<String, String> getResponseAttributes() {
        return responseAttributes;
    }

    public void setResponseAttributes(Map<String, String> responseAttributes) {
        this.responseAttributes = responseAttributes;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Map<String, String> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public String getDatastoreName() {
        return datastoreName;
    }

    public void setDatastoreName(String datastoreName) {
        this.datastoreName = datastoreName;
    }

    public void readDatastoreResponse(DatastoreItem ds, DatastoreAuthResponse response) {
        this.datastoreId = ds.getId();
        this.datastoreName = ds.getName();
        this.responseAttributes = response.getResponseAttributes();
        this.sessionAttributes = response.getSessionAttributes();
    }
}
