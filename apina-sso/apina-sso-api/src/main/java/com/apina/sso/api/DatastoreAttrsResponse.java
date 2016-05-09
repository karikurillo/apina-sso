package com.apina.sso.api;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kari Kurillo on 9.5.2016.
 */
public class DatastoreAttrsResponse {
    private Set<String> groups;
    private Set<String> roles;
    private Map<String, String> attributes;
    private boolean validUser = false;

    public DatastoreAttrsResponse() {
        this.groups = Collections.EMPTY_SET;
        this.roles = Collections.EMPTY_SET;
        this.attributes = Collections.EMPTY_MAP;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }
}
