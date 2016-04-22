package com.apina.sso.api;

import java.util.Map;

/**
 *
 */
public abstract class AbstractDatastore implements Datastore {

    public abstract void configureDatastore(String realm, Map<String, String> configuration) throws Exception;
    public abstract DatastoreAuthenticationResponse authenticateUser(String username, String password) throws Exception;
    public abstract Map<String, String> getUserAttributes(String username, String token) throws Exception;

    public DatastoreAuthenticationResponse authenticate(String username, String password) throws Exception {
        return authenticateUser(username, password);
    }

    public Map<String, String> attributes(String username, String token) throws Exception {
        return getUserAttributes(username, token);
    }

    public void configure(String realm, Map<String, String> configuration) throws Exception {
        configureDatastore(realm, configuration);
    }


}
