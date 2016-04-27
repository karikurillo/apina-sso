package com.apina.sso.core.config;

import com.apina.sso.core.realm.RealmManager;
import com.apina.sso.core.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
@Scope("singleton")
public class CoreConfigurationManager {

    private static final Logger logger = LoggerFactory.getLogger(CoreConfigurationManager.class);

    @Autowired
    private RealmManager realmManager;

    @Autowired
    private SessionManager sessionManager;

    public static ApplicationContext ctx;

    private ConfigurationType configurationType;
    private Configurator configurator;

    public CoreConfigurationManager() {

    }

    public void initialize(String... args) throws Exception {
        logger.info("Parsing commandline arguments to determine Configurator...");
        initConfigurationFromFile(args[0]);

        realmManager.initializeDatastores();
        sessionManager.initializeCache();
    }

    private void initConfigurationFromFile(String path) throws Exception {
        configurationType = ConfigurationType.FILE;
        configurator = ctx.getBean(FileConfigurator.class);

        Map<String, String> configuration = new HashMap<String, String>();
        configuration.put(FileConfigurator.FILE_PATH, path);
        configurator.initialize(configuration);
    }

    private void initConfigurationFromDb() {
        configurationType = ConfigurationType.DB;
    }

    @Autowired
    private void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }
}
