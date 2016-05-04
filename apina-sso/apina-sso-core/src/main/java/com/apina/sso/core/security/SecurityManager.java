package com.apina.sso.core.security;

import com.apina.sso.core.realm.RealmAuthResponse;
import com.apina.sso.core.realm.RealmAuthStatus;
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

    public AuthenticationResponse authenticateUser(String realm, String username, String password) throws Exception {
        RealmAuthResponse realmAuthResponse = realmManager.authenticateUser(realm, username, password);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(sessionManager.createSession(realm, username, null));
        authenticationResponse.setRealmAuthResponse(realmAuthResponse);
        authenticationResponse.setAuthenticated(realmAuthResponse.getRealmAuthStatus() == RealmAuthStatus.LOGIN_SUCCESSFUL);

        return authenticationResponse;
    }

    public boolean logoutToken(String token) {
        try {
            return sessionManager.logout(token);
        } catch (Exception e) {
            logger.error("Could not logout session with token " + token);
            return false;
        }
    }
}
