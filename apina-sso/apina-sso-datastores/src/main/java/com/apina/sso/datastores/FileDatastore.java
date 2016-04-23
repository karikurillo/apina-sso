package com.apina.sso.datastores;

import com.apina.sso.api.AbstractDatastore;
import com.apina.sso.api.DatastoreAuthResponse;
import com.apina.sso.api.DatastoreAuthStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class FileDatastore extends AbstractDatastore {

    private static final Logger logger = LoggerFactory.getLogger(FileDatastore.class);

    protected String dataFile;
    protected boolean cached = true;

    class User {
        public String uid;
        public String password;
        public String fullName;
        public Set<String> groups;
        public Set<String> roles;
    }

    protected Map<String, User> userCache = new HashMap<String, User>();

    @Override
    public void configureDatastore(String realm, Map<String, String> configuration) throws Exception {

        logger.info("Configuring FileDatastore");

        // Get data-file path
        dataFile = configuration.get("data-file");
        if (dataFile == null || dataFile.isEmpty()) {
            throw new Exception("FileDatastore configuration parameter \"data-file\" is missing or empty");
        }

        if (cached) parseDataFile(dataFile);
    }

    protected Map<String, User> parseDataFile(String dsDataFile) throws Exception {
        Map<String, User> users = new HashMap<String, User>();
        logger.info("Parsing FileDatastore data-file " + dsDataFile + "...");
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
        return users;
    }

    @Override
    public DatastoreAuthResponse authenticateUser(String username, String password) throws Exception {
        Map<String, User> users = cached ? this.userCache : parseDataFile(this.dataFile);

        return new DatastoreAuthResponse(DatastoreAuthStatus.USER_NOT_FOUND);
    }

    @Override
    public Map<String, String> getUserAttributes(String username, String token) throws Exception {
        return null;
    }
}
