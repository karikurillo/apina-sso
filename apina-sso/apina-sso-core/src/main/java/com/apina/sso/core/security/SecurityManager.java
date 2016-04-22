package com.apina.sso.core.security;

import com.apina.sso.core.realm.RealmManager;
import com.apina.sso.core.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Kari Kurillo on 22.4.2016.
 */
@Component
@Scope("singleton")
public class SecurityManager {

    private static final Logger logger = LoggerFactory.getLogger(SecurityManager.class);

    @Autowired
    private RealmManager realmManager;

    @Autowired
    private SessionManager sessionManager;

    public SecurityManager() {}

    public void authenticateUser(String realm, String username, String password) throws Exception {
        realmManager.authenticateUser(realm, username, password);
        sessionManager.createSession("", "", null);
    }
}