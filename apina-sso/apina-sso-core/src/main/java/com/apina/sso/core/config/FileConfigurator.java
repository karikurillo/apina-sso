package com.apina.sso.core.config;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 */
public class FileConfigurator implements Configurator {

    protected String configuration;

    public FileConfigurator(String configuration) {
        this.configuration = configuration;
    }

    @Override
    public void initialize() throws Exception {
        parseConfiguration();
    }

    protected void parseConfiguration() throws Exception {
        // Read and parse data file
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(this.configuration));
            JSONObject configuration = (JSONObject)jsonObject.get("configuration");
            JSONArray realms = (JSONArray)configuration.get("realms");

            /*

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
            */
        } catch (FileNotFoundException e) {
            throw new Exception("FileConfigurator configuration file has invalid path: " + this.configuration);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
