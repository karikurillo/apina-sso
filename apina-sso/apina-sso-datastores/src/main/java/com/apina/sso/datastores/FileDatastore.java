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
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class FileDatastore extends AbstractDatastore {

    private static final Logger logger = LoggerFactory.getLogger(FileDatastore.class);

    public static final String ATTRIBUTE_DATA_FILE = "dataFile";

    protected String dataFile;
    protected boolean cached = true;

    class User {
        public String uid;
        public String password;
        public String fullName;
        public Set<String> groups;
        public Set<String> roles;
        public Map<String, String> attributes;
    }

    protected Map<String, User> userCache = new HashMap<String, User>();

    @Override
    public void configureDatastore(String realm, Map<String, String> configuration) throws Exception {

        logger.info("Configuring FileDatastore");

        // Get data-file path
        dataFile = configuration.get(FileDatastore.ATTRIBUTE_DATA_FILE);
        if (dataFile == null || dataFile.isEmpty()) {
            throw new Exception("FileDatastore configuration parameter \"" + FileDatastore.ATTRIBUTE_DATA_FILE + "\" is missing or empty");
        }

        if (cached) parseDataFile(dataFile);
    }

    protected Map<String, User> parseDataFile(String dsDataFile) throws Exception {
        Map<String, User> users = new HashMap<String, User>();
        logger.info("Parsing FileDatastore data from file: " + dsDataFile + "...");
        // Read and parse data file
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(dsDataFile));

            Map<String, List<String>> groups = parseGroups((JSONArray) jsonObject.get("groups"));
            JSONArray usersArray = (JSONArray)jsonObject.get("users");

            for (Object user : usersArray) {
                User u = new User();
                u.uid = (String)((JSONObject)user).get("uid");
                u.fullName = (String)((JSONObject)user).get("fullName");
                u.password = (String)((JSONObject)user).get("password");
                parseUserGroups(u, (JSONArray)((JSONObject)user).get("groups"), groups);
                parseUserAttributes(u, (JSONObject)((JSONObject)user).get("attributes"));
                users.put(u.uid, u);
            }

            logger.info("FileDatastore data-file parsing done. User count: " + users.size());
        } catch (FileNotFoundException e) {
            throw new Exception("FileDatastore configuration parameter \"" + FileDatastore.ATTRIBUTE_DATA_FILE + "\" has invalid path: " + dsDataFile);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return users;
    }

    protected void parseUserGroups(User user, JSONArray groupsArray, Map<String, List<String>> groups) {
        user.groups = new HashSet<String>();
        user.roles = new HashSet<String>();
        for (Object group : groupsArray) {
            user.groups.add((String)group);
            user.roles.addAll(groups.get((String)group));
        }
    }

    protected void parseUserAttributes(User user, JSONObject attributes) {
        user.attributes = new HashMap<String, String>();
        for (Object key : attributes.keySet()) {
            user.attributes.put((String)key, (String)attributes.get(key));
        }
    }

    protected Map<String, List<String>> parseGroups(JSONArray groupsArray) {
        Map<String, List<String>> groups = new HashMap<String, List<String>>();
        for (Object group : groupsArray) {
            groups.put((String)((JSONObject)group).get("name"), parseRoles( (JSONArray)((JSONObject) group).get("roles") ));
        }
        return groups;
    }

    protected List<String> parseRoles(JSONArray rolesArray) {
        // @ TODO Can this handle empty list of roles (no roles)
        List<String> roles = Arrays.asList((String[])rolesArray.toArray(new String[rolesArray.size()]));
        return roles;
    }

    @Override
    public DatastoreAuthResponse authenticateUser(String username, String password) throws Exception {
        Map<String, User> users = cached ? this.userCache : parseDataFile(this.dataFile);
        DatastoreAuthResponse response = new DatastoreAuthResponse(DatastoreAuthStatus.USER_NOT_FOUND);
        User user = users.get(username);

        if (user != null) {
            // Check password
            if (user.password.compareTo(password) == 0) {
                response.setDatastoreAuthStatus(DatastoreAuthStatus.LOGIN_SUCCESSFUL);
            } else {
                response.setDatastoreAuthStatus(DatastoreAuthStatus.INVALID_PASSWORD);
            }
        }

        return response;
    }

    @Override
    public Map<String, String> getUserAttributes(String username, String token) throws Exception {
        return null;
    }
}
