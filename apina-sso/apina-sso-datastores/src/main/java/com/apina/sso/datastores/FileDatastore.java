package com.apina.sso.datastores;

import com.apina.sso.api.AbstractDatastore;
import com.apina.sso.api.AuthenticationEnum;
import com.apina.sso.api.AuthenticationResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class FileDatastore extends AbstractDatastore {

    protected String dataFile;

    class User {
        public String uid;
        public String password;
        public String fullName;
        public Set<String> groups;
        public Set<String> roles;
    }

    protected Map<String, User> users = new HashMap<String, User>();

    @Override
    public void configureDatastore(String realm, Map<String, String> configuration) throws Exception {
        // Get data-file path
        dataFile = configuration.get("data-file");


        if (dataFile == null || dataFile.isEmpty()) {
            throw new Exception("FileDatastore configuration parameter \"data-file\" is missing or empty");
        }

        parseDataFile(dataFile);
    }

    protected void parseDataFile(String dsDataFile) throws Exception {
        // Read and parse data file
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(dsDataFile));

            String name = (String) jsonObject.get("name");
            System.out.println(name);

            long age = (Long) jsonObject.get("age");
            System.out.println(age);

            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("messages");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (FileNotFoundException e) {
            throw new Exception("FileDatastore configuration parameter \"data-file\" has invalid path: " + dsDataFile);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AuthenticationResponse authenticateUser(String username, String password) throws Exception {
        return new AuthenticationResponse(AuthenticationEnum.USER_NOT_FOUND);
    }

    @Override
    public Map<String, String> getUserAttributes(String username, String token) throws Exception {
        return null;
    }
}
