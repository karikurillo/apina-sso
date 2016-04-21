package com.apina.sso.core.config;

import com.apina.sso.core.realm.DatastoreItem;
import com.apina.sso.core.realm.RealmItem;
import com.apina.sso.core.realm.RealmManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 *
 */
@Component
public class FileConfigurator implements Configurator {

    public final static String FILE_PATH = "FILE_PATH";

    @Autowired
    private RealmManager realmManager;

    public FileConfigurator() {    }

    @Override
    public void initialize(Map<String, String> configuration) throws Exception {
        parseConfiguration(configuration.get(FILE_PATH));
    }

    private void parseConfiguration(String configurationFile) throws Exception {
        // Read and parse data file
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(configurationFile));
            JSONObject configuration = (JSONObject)jsonObject.get("configuration");
            JSONArray realms = (JSONArray)configuration.get("realms");

            for (Object realmJson : realms) {
                RealmItem realm = parseRealm((JSONObject)realmJson);
                realmManager.addRealm(realm);
            }

        } catch (FileNotFoundException e) {
            throw new Exception("FileConfigurator configuration file has invalid path: " + configurationFile);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private RealmItem parseRealm(JSONObject realmJson) {
        RealmItem realm = new RealmItem();
        realm.setName((String)realmJson.get("name"));
        JSONArray datastores = (JSONArray)realmJson.get("datastores");

        for (Object datastoreJson : datastores) {
            realm.addDatastore(parseDatastore((JSONObject) datastoreJson));
        }

        return realm;
    }

    private DatastoreItem parseDatastore(JSONObject datastoreJson) {
        DatastoreItem datastore = new DatastoreItem();
        datastore.setName((String)datastoreJson.get("name"));
        datastore.setClassName((String) datastoreJson.get("class"));
        //JSONArray configuration = (JSONArray)datastoreJson.get("configuration");
        JSONObject configuration = (JSONObject)datastoreJson.get("configuration");
        for (Object key : configuration.keySet()) {
            datastore.addConfigurationParameter((String)key, (String)configuration.get(key));
        }

        return datastore;
    }

}
