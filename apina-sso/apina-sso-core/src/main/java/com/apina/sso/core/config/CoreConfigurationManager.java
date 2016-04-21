package com.apina.sso.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
@Scope("singleton")
public class CoreConfigurationManager {


    public static ApplicationContext ctx;

    private ConfigurationType configurationType;
    private Configurator configurator;

    public CoreConfigurationManager() {

    }

    public void initConfigurationFromFile(String path) throws Exception {
        configurationType = ConfigurationType.FILE;
        configurator = ctx.getBean(FileConfigurator.class);

        Map<String, String> configuration = new HashMap<String, String>();
        configuration.put(FileConfigurator.FILE_PATH, path);
        configurator.initialize(configuration);

        System.out.println(configurator);
    }

    public void initConfigurationFromDb() {
        configurationType = ConfigurationType.DB;
    }

    @Autowired
    private void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }
}
