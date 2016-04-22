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

    public static void main(String args[]) {
        SpringApplication.run(ApinaSso.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            configurationManager.initialize(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
