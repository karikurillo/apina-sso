package com.apina.sso.core;

import com.apina.sso.core.config.CoreConfigurationManager;

/**
 * Created by Kari Kurillo on 19/04/16.
 */
public class ApinaSso {

    CoreConfigurationManager configurationManager;

    public static void main(String args[]) {
        ApinaSso apinaSso = new ApinaSso();
        try {
            apinaSso.initialize(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApinaSso() {}

    public void initialize(String args[]) throws Exception {
        configurationManager = new CoreConfigurationManager();
        configurationManager.initConfigurationFromFile(args[0]);
    }


}
