package com.apina.sso.core;

import com.apina.sso.core.config.CoreConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Kari Kurillo on 19/04/16.
 */
@SpringBootApplication
public class ApinaSso implements CommandLineRunner {

    @Autowired
    private CoreConfigurationManager configurationManager;

    //SpringApplication.run(SampleSimpleApplication.class, args);

    public static void main(String args[]) {
        SpringApplication.run(ApinaSso.class, args);
        /*
        ApinaSso apinaSso = new ApinaSso();
        try {
            apinaSso.initialize(args);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void run(String... args) {
        try {
            configurationManager.initConfigurationFromFile(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //public ApinaSso() {}

    public void initialize(String args[]) throws Exception {
        //configurationManager = new CoreConfigurationManager();
        //configurationManager.initConfigurationFromFile(args[0]);
    }


}
