package com.apina.sso.core.services.v1.pojos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kari Kurillo on 22.4.2016.
 */
public class RestResponseAuthentication {
    private String token;
    private String message;
    private Map<String, String> attributes = new HashMap<String, String>();

    public RestResponseAuthentication(String token, String message) {
        this.token = token;
        this.message = message;
    }
    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    public String getToken() {
        return this.token;
    }

    public String getMessage() {
        return this.message;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }
}
