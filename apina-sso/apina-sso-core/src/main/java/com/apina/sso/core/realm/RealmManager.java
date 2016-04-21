package com.apina.sso.core.realm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
@Scope("singleton")
public class RealmManager {

    private Map<String, RealmItem> realms;

    public RealmManager() {
        this.realms = new HashMap<String, RealmItem>();
    }

    public void addRealm(RealmItem realm) {
        this.realms.put(realm.getName(), realm);
    }
}
