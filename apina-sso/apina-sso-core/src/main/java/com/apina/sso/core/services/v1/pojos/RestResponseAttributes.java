package com.apina.sso.core.services.v1.pojos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kari Kurillo on 9.5.2016.
 */
public class RestResponseAttributes {
    private Set<String> groups = new HashSet<String>();
    private Set<String> roles = new HashSet<String>();
    private Map<String, String> attributes = new HashMap<String, String>();

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setGroups(Set<String> groups) {
        for (String group : groups) {
            this.groups.add(group);
        }
    }

    public void setRoles(Set<String> roles) {
        for (String role : roles) {
            this.roles.add(role);
        }
    }

    public void setAttributes(Map<String, String> userAttributes) {
        for (Map.Entry<String, String> entry : userAttributes.entrySet()) {
            this.attributes.put(entry.getKey(), entry.getValue());
        }
    }
}

