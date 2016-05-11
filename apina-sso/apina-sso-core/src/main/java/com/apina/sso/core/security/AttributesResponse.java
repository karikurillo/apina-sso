package com.apina.sso.core.security;

import com.apina.sso.core.session.SessionInfo;

import java.util.*;

/**
 * Created by Kari Kurillo on 9.5.2016.
 */
public class AttributesResponse {
    private SessionInfo sessionInfo;
    private Set<String> groups;
    private Set<String> roles;
    private Map<String, String> userAttributes;

    public AttributesResponse() {
        this.userAttributes = new HashMap<String, String>();
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public Map<String, String> getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(Map<String, String> attributes) {
        if (attributes != null) this.userAttributes.putAll(attributes);
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
