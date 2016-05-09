package com.apina.sso.core.services.v1;

import com.apina.sso.core.realm.RealmManager;
import com.apina.sso.core.security.AuthenticationResponse;
import com.apina.sso.core.security.SecurityManager;
import com.apina.sso.core.services.v1.pojos.RestResponseAuthentication;
import com.apina.sso.core.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
public class RestAuthenticationV1 {

    @Autowired
    private SecurityManager securityManager;

    @RequestMapping(value = "/v1/authenticate", method = RequestMethod.GET)
    public RestResponseAuthentication authenticate(@RequestHeader(value="X-ApinaSSO-Username") String username, @RequestHeader(value="X-ApinaSSO-Password") String password) {
        return authenticateUser(RealmManager.ROOT_REALM, username, password);
    }

    @RequestMapping(value = "/v1/{realm}/authenticate", method = RequestMethod.GET)
    public RestResponseAuthentication authenticate(@RequestHeader(value="X-ApinaSSO-Username") String username, @RequestHeader(value="X-ApinaSSO-Password") String password, @PathVariable(value = "realm") String realm) {
        return authenticateUser(realm, username, password);
    }

    private RestResponseAuthentication authenticateUser(String realm, String username, String password) {
        // @TODO Add support for custom authentication parameters in the request body
        AuthenticationResponse authenticationResponse = null;
        try {
            authenticationResponse = securityManager.authenticateUser(realm, username, password);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Authentication failed: " + ex.getMessage());
        }

        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            RestResponseAuthentication response = new RestResponseAuthentication(authenticationResponse.getToken(), "Authentication successful");
            response.copyAttributes(response.getAttributes());
            return response;
        }
        return null;
    }
}
