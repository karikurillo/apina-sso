package com.apina.sso.datastores;

import com.apina.sso.api.AbstractDatastore;
import com.apina.sso.api.DatastoreAttrsResponse;
import com.apina.sso.api.DatastoreAuthResponse;

import java.util.Map;

/**
 *
 */
public class JdbcDatastore extends AbstractDatastore {

    @Override
    public void configureDatastore(String realm, Map<String, String> configuration) throws Exception {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public DatastoreAuthResponse authenticateUser(String username, String password) throws Exception {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public DatastoreAttrsResponse getUserAttributes(String username, String token, Map<String, String> sessionAttributes) throws Exception {
        throw new RuntimeException("Method not implemented");
    }
}
