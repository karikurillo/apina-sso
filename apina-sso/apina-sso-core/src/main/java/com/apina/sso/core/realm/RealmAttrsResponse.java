package com.apina.sso.core.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kari Kurillo on 9.5.2016.
 */
public class RealmAttrsResponse {
    private Set<String> groups;
    private Set<String> roles;
    private Map<String, String> userAttributes;

    public RealmAttrsResponse() {
        this.groups = new HashSet<String>();
        this.roles = new HashSet<String>();
        this.userAttributes = new HashMap<String, String>();
    }

    public Map<String, String> getUserAttributes() {
        return userAttributes;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void addUserAttributes(Map<String, String> attributes) {
        if (attributes != null) this.userAttributes.putAll(attributes);
    }

    public void addRoles(Set<String> roles) {
        if (roles != null) this.roles.addAll(roles);
    }

    public void addGroups(Set<String> groups) {
        if (groups != null) this.groups.addAll(groups);
    }
}
