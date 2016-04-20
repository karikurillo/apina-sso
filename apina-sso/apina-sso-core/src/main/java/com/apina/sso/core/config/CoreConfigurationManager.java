package com.apina.sso.core.config;

/**
 *
 */
public class CoreConfigurationManager {

    private ConfigurationType configurationType;
    private Configurator configurator;

    public CoreConfigurationManager() {

    }

    public void initConfigurationFromFile(String path) throws Exception {
        configurationType = ConfigurationType.FILE;
        configurator = new FileConfigurator(path);
        configurator.initialize();
    }

    public void initConfigurationFromDb() {
        configurationType = ConfigurationType.DB;
    }
}
