package com.apina.sso.datastores;

import com.apina.sso.api.AbstractDatastore;
import com.apina.sso.api.DatastoreAuthenticationResponse;

import java.util.Map;

/**
 *
 */
public class JdbcDatastore extends AbstractDatastore {
    @Override
    public void configureDatastore(String realm, Map<String, String> configuration) throws Exception {

    }

    @Override
    public DatastoreAuthenticationResponse authenticateUser(String username, String password) throws Exception {
        return null;
    }

    @Override
    public Map<String, String> getUserAttributes(String username, String token) throws Exception {
        return null;
    }
}
