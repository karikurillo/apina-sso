package com.apina.sso.core.security;

import com.apina.sso.core.exceptions.InvalidTokenException;
import com.apina.sso.core.realm.RealmAttrsResponse;
import com.apina.sso.core.realm.RealmAuthResponse;
import com.apina.sso.core.realm.RealmAuthStatus;
import com.apina.sso.core.realm.RealmManager;
import com.apina.sso.core.session.SessionInfo;
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

    public SecurityManager() {
        logger.info("Initializing SecurityManager...");
    }

    public AuthenticationResponse authenticateUser(String realm, String username, String password) throws Exception {
        RealmAuthResponse realmAuthResponse = realmManager.authenticateUser(realm, username, password);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (realmAuthResponse.getRealmAuthStatus() == RealmAuthStatus.LOGIN_SUCCESSFUL) {
            authenticationResponse.setToken(sessionManager.createSession(realm, username, realmAuthResponse.getSessionAttributes()));
            authenticationResponse.setRealmAuthResponse(realmAuthResponse);
            authenticationResponse.setAuthenticated(realmAuthResponse.getRealmAuthStatus() == RealmAuthStatus.LOGIN_SUCCESSFUL);
            logger.info("Authentication successful: realm=" + realm + ", user=" + username + ", token=" + authenticationResponse.getToken());
        } else {
            logger.info("Authentication failed: realm=" + realm + ", user=" + username);
        }

        return authenticationResponse;
    }

    public boolean logoutToken(String token) {
        try {
            if (sessionManager.logout(token)) {
                logger.info("Logout successful: token=" + token);
                return true;
            }
        } catch (Exception e) {
            logger.error("Could not logout session with token " + token);
        }
        return false;
    }

    public AttributesResponse getUserAttributes(String token) throws Exception {
        AttributesResponse attributesResponse = new AttributesResponse();
        SessionInfo sessionInfo = sessionManager.getSessionInfo(token);
        attributesResponse.setSessionInfo(sessionInfo);

        if (!sessionInfo.isSessionValid()) {
            logger.info("Invalid token: " + token);
            throw new InvalidTokenException("Invalid token: " + token);
        } else {
            RealmAttrsResponse realmAttrsResponse = realmManager.getUserAttributes(sessionInfo.getRealm(), sessionInfo.getUsername(), sessionInfo.getToken(), sessionInfo.getSessionAttributes());
            attributesResponse.setGroups( realmAttrsResponse.getGroups() );
            attributesResponse.setRoles( realmAttrsResponse.getRoles() );
            // Attributes from datastore
            attributesResponse.setUserAttributes( realmAttrsResponse.getUserAttributes() );
        }

        return attributesResponse;
    }
}
