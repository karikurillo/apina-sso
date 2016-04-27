package com.apina.sso.core.realm;

import com.apina.sso.api.Datastore;
import com.apina.sso.api.DatastoreAuthResponse;
import com.apina.sso.datastores.FileDatastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 */
@Component
@Scope("singleton")
public class RealmManager {

    public final static String ROOT_REALM = "/";

    private static final Logger logger = LoggerFactory.getLogger(RealmManager.class);

    private Map<String, RealmItem> realms;

    public RealmManager() {
        this.realms = new TreeMap<String, RealmItem>();
    }

    public void addRealm(RealmItem realm) {
        this.realms.put(realm.getName(), realm);
    }

    public int getRealmCount() {
        return this.realms.size();
    }

    public RealmItem getRealm(String name) {
        return this.realms.get(name);
    }

    public void initializeDatastores() {
        logger.info("Initializing realm datastores...");
        for (Map.Entry<String, RealmItem> entry : this.realms.entrySet()) {
            logger.info("Realm \"" + entry.getKey() + "\" has " + entry.getValue().getDatastores().size() + " datastore(s)");

            for (DatastoreItem ds : entry.getValue().getDatastores()) {
                logger.info("Configuring datastore \"" + ds.getName() +"\"...");
                try {
                    // @ TODO Dynamic creation
                    Datastore dsImpl = new FileDatastore();
                    dsImpl.configure(entry.getKey(), ds.getConfiguration());

                    ds.setDatastore(dsImpl);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    public RealmAuthResponse authenticateUser(String realmName, String username, String password) throws Exception {
        RealmItem realm = realms.get(realmName);

        if (realm == null) throw new Exception("Invalid realm name: " + realmName);

        RealmAuthResponse response = new RealmAuthResponse(realmName);
        logger.trace("Authenticating to realm \"" + realmName + "\"...");
        for (DatastoreItem ds : realm.getDatastores()) {
            logger.trace("Authenticating to datastore \"" + ds.getName() + "\"...");
            DatastoreAuthResponse dsResponse = ds.getDatastore().authenticate(username, password);
            switch (dsResponse.getDatastoreAuthStatus()) {
                case LOGIN_SUCCESSFUL:
                    response.readDatastoreResponse(ds, dsResponse);
                    response.setRealmAuthStatus(RealmAuthStatus.LOGIN_SUCCESSFUL);
                    return response;

                case LOGIN_FAILED:
                    response.readDatastoreResponse(ds, dsResponse);
                    response.setRealmAuthStatus(RealmAuthStatus.LOGIN_FAILED);
                    return response;

                case INVALID_PASSWORD:
                    response.readDatastoreResponse(ds, dsResponse);
                    response.setRealmAuthStatus(RealmAuthStatus.INVALID_PASSWORD);
                    return response;


                case USER_NOT_FOUND:
                    // Continue to next datastore
                    logger.debug("User \"" + username + "\" not found from datastore " + ds.toString());
                    break;
            }

        }
        return response;
    }

    public Set<String> getRealmNames() {
        return realms.keySet();
    }
}
