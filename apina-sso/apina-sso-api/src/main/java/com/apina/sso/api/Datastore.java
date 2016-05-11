package com.apina.sso.api;

import java.util.Map;

/**
 *
 */
public interface Datastore {
    DatastoreAuthResponse authenticate(String username, String password) throws Exception;
    DatastoreAttrsResponse attributes(String username, String token, Map<String, String> sessionAttributes) throws Exception;
    void configure(String realm, Map<String, String> configuration) throws Exception;
}
