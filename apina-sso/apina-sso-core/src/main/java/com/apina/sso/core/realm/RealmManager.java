package com.apina.sso.core.realm;

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

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}
