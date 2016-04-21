package com.apina.sso.core.realm;

import com.apina.sso.api.Datastore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kari Kurillo on 21/04/16.
 */
public class DatastoreItem {
    private String name;
    private String className;
    private Datastore datastore;
    private Map<String, String> configuration = new HashMap<String, String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void addConfigurationParameter(String key, String value) {
        this.configuration.put(key, value);
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
