package com.apina.sso.core.realm;


import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RealmItem {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DatastoreItem> getDatastores() {
        return datastores;
    }

    public void addDatastore(DatastoreItem datastore) {
        this.datastores.add(datastore);
    }

    private String name;
    private List<DatastoreItem> datastores = new ArrayList<DatastoreItem>();
}
